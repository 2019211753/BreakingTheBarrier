package com.lrm.web;

import com.lrm.enumeration.DonationGrow;
import com.lrm.enumeration.ImpactGrow;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点赞点踩类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-09
 */
@RestController
public class ApproveController {
    @Autowired
    QuestionServiceImpl questionServiceImpl;

    @Autowired
    BlogServiceImpl blogServiceImpl;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    LikesServiceImpl likesServiceImpl;

    @Autowired
    DisLikesServiceImpl dislikesServiceImpl;

    @Autowired
    CommentServiceImpl commentServiceImpl;

    /**
     * 点赞/取消赞问题
     *
     * @param request    获得执行赞动作的用户id
     * @param questionId 问题Id
     */
    @GetMapping("/question/{questionId}/approve")
    public Result approveQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        return approve(question, questionServiceImpl, request);
    }

    /**
     * 点赞/取消赞博客
     *
     * @param request    获得执行赞动作的用户id
     * @param blogId 博客Id
     */
    @GetMapping("/blog/{blogId}/approve")
    public Result approveBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        return approve(blog, blogServiceImpl, request);
    }

    public <E extends Template> Result approve(E e, TemplateServiceImpl<E> repository, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(4);
        Long postUserId = TokenInfo.getCustomUserId(request);

        //得到消息接受双方
        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = e.getUser();

        //如果存在点赞对象 就删除 即取消点赞 否则点赞
        Likes likes = likesServiceImpl.get(postUser, e);
        DisLikes dislikes = dislikesServiceImpl.get(postUser, e);

        if (likes != null) {
            likesServiceImpl.delete(likes);

            dealLikes(e, receiveUser, -1, repository);
            hashMap.put("approved", false);
            hashMap.put("disapproved", dislikes != null);
            hashMap.put("likesNum", e.getLikesNum());
            hashMap.put("disLikesNum", e.getDisLikesNum());
            return new Result(hashMap, "取消赞成功");
        } else {
            //若点过踩 取消点踩
            if (dislikes != null) {
                dislikesServiceImpl.delete(dislikes);

                hideTemplate(e, -1, repository);
            }

            Likes likes1 = new Likes();

            likesServiceImpl.save(e, likes1, postUser, receiveUser);

            //处理部分属性
            dealLikes(e, receiveUser, 1, repository);
            hashMap.put("approved", true);
            hashMap.put("disapproved", false);
            hashMap.put("likesNum", e.getLikesNum());
            hashMap.put("disLikesNum", e.getDisLikesNum());
            return new Result(hashMap, "点赞成功");
        }


    }


    /**
     * 点踩/取消踩问题
     *
     * @param request    获取执行踩动作的用户id
     * @param questionId 问题Id
     */
    @GetMapping("/question/{questionId}/disapprove")
    public Result disapproveQuestion(@PathVariable Long questionId, HttpServletRequest request)
    {
        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

       return disapprove(question, questionServiceImpl, request);
    }

    /**
     * 点踩/取消踩博客
     *
     * @param request    获取执行踩动作的用户id
     * @param blogId 博客Id
     */
    @GetMapping("/blog/{blogId}/disapprove")
    public Result disapproveBlog(@PathVariable Long blogId, HttpServletRequest request)
    {
        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        return disapprove(blog, blogServiceImpl, request);
    }

    <E extends Template> Result disapprove(E e, TemplateServiceImpl<E> repository, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(4);

        Long postUserId = TokenInfo.getCustomUserId(request);
        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = e.getUser();

        //若点过踩 取消点踩 否则点踩
        Likes likes = likesServiceImpl.get(postUser, e);
        DisLikes dislikes = dislikesServiceImpl.get(postUser, e);
        if (dislikes != null) {
            dislikesServiceImpl.delete(dislikes);

            hideTemplate(e, -1, repository);
            hashMap.put("approved", likes != null);
            hashMap.put("disapproved", false);
            hashMap.put("likesNum", e.getLikesNum());
            hashMap.put("disLikesNum", e.getDisLikesNum());
            return new Result(hashMap, "取消踩成功");
        } else {
            //若点过赞 取消点赞 贡献值复原
            if (likes != null) {
                likesServiceImpl.delete(likes);
                dealLikes(e, receiveUser, -1, repository);
            }

            DisLikes dislikes1 = new DisLikes();

            dislikesServiceImpl.save(e, dislikes1, postUser);
            hideTemplate(e, 1, repository);
            hashMap.put("approved", false);
            hashMap.put("disapproved", true);
            hashMap.put("likesNum", e.getLikesNum());
            hashMap.put("disLikesNum", e.getDisLikesNum());
            return new Result(hashMap, "点踩成功");
        }
    }


    /**
     * 给评论点赞
     *
     * @param commentId 被点赞的评论Id
     * @param request 获取执行点赞动作的用户Id
     */
    @GetMapping("comment/{commentId}/approve")
    public Result approve(@PathVariable Long commentId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Comment comment = commentServiceImpl.getComment(commentId);

        //只能给有效问题点赞
        if (comment.getAnswer()) {

            //当前用户 点赞的人
            Long postUserId = TokenInfo.getCustomUserId(request);
            User postUser = userServiceImpl.getUser(postUserId);
            //被点赞的人
            User receiveUser = comment.getReceiveUser();

            Likes likes = likesServiceImpl.get(postUser, comment);

            //点过赞删除，无则增加
            if (likes != null) {
                //取消点赞前的最高赞数 并处理
                deleteCommentLikes(comment, likes, receiveUser);
                hashMap.put("approved", false);
                hashMap.put("likesNum", comment.getLikesNum());
                hashMap.put("disLikesNum", comment.getDisLikesNum());
                return new Result(hashMap, "取消赞成功");
            } else {
                //若点过踩 取消点踩
                DisLikes disLikes = dislikesServiceImpl.get(postUser, comment);
                if (disLikes != null) {
                    dislikesServiceImpl.delete(disLikes);
                    //复原
                    hideComment(comment, -1);
                }

                Likes likes1 = new Likes();

                likes1.setComment(comment);

                if (comment.getQuestion() != null) {
                    Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByQuestionId(comment.getQuestion().getId()));
                    likesServiceImpl.save(comment.getQuestion(), likes1, postUser, receiveUser);
                    dealLikes(receiveUser, comment, comment.getQuestion(), maxNum0, 1, questionServiceImpl);
                }
                //取消点赞前的最高赞数 并处理
                if (comment.getBlog() != null) {
                    Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByBlogId(comment.getBlog().getId()));
                    likesServiceImpl.save(comment.getBlog(), likes1, postUser, receiveUser);
                    //对impact属性的处理
                    dealLikes(receiveUser, comment, comment.getBlog(), maxNum0, 1, blogServiceImpl);
                }
                hashMap.put("approved", true);
                hashMap.put("likesNum", comment.getLikesNum());
                hashMap.put("disLikesNum", comment.getDisLikesNum());
                return new Result(hashMap, "点赞成功");
            }
        } else {
            throw new FailedOperationException("不能对灌水评论点赞");
        }
    }


    /**
     * 点踩 到标准就隐藏
     *
     * @param request 获取执行点踩动作的用户Id
     * @param commentId 评论Id
     */
    @GetMapping("comment/{commentId}/disapprove")
    public Result disapprove(@PathVariable Long commentId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Comment comment = commentServiceImpl.getComment(commentId);

        Long postUserId = TokenInfo.getCustomUserId(request);
        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = comment.getReceiveUser();

        //若点过踩 取消点踩 否则点踩
        DisLikes disLikes = dislikesServiceImpl.get(postUser, comment);
        if (disLikes != null) {
            dislikesServiceImpl.delete(disLikes);

            hideComment(comment, -1);
            hashMap.put("disapproved", false);
            hashMap.put("likesNum", comment.getLikesNum());
            hashMap.put("disLikesNum", comment.getDisLikesNum());
            return new Result(hashMap, "取消踩成功");
        } else {
            //若点过赞 取消点赞 贡献值复原
            Likes likes = likesServiceImpl.get(postUser, comment);
            if (likes != null) {
                deleteCommentLikes(comment, likes, receiveUser);
            }

            DisLikes disLikes1 = new DisLikes();

            disLikes1.setPostUser(postUser);
            disLikes1.setComment(comment);
            dislikesServiceImpl.save(disLikes1);

            hideComment(comment, 1);
            hashMap.put("disapproved", true);
            hashMap.put("likesNum", comment.getLikesNum());
            hashMap.put("disLikesNum", comment.getDisLikesNum());
            return new Result(hashMap, "点踩成功");
        }
    }

    /**
     * 处理问题/博客的点赞
     */
    <E extends Template> void dealLikes(E e, User receiveUser, int p, TemplateServiceImpl<E> repository) {
        //不明白为什么把他们放在saveLikes之前就可以update
        e.setLikesNum(e.getLikesNum() + p * 1);

        //点赞对问题/博客影响力
        e.setImpact(e.getImpact() + p * ImpactGrow.APPROVED.getGrow());

        //问题被（取消）点赞 提问者贡献值+-
        receiveUser.setDonation(receiveUser.getDonation() + p * DonationGrow.TEMPLATE_APPROVED.getGrow());

        repository.save(e);
        userServiceImpl.saveUser(receiveUser);
    }

    /**
     * 被踩到一定程度隐藏问题/博客
     */
    <E extends Template> void hideTemplate(E e, int p, TemplateServiceImpl<E> repository) {
        e.setDisLikesNum(e.getDisLikesNum() + p);

        if ((e.getDisLikesNum() >= Magic.HIDE_STANDARD1) & (e.getLikesNum() <= Magic.HIDE_STANDARD2 * e.getDisLikesNum())) {
            e.setHidden(true);
        } else {
            e.setHidden(false);
        }
        repository.save(e);
    }


    /**
     * 被踩到一定程度隐藏评论
     */
    void hideComment(Comment comment, int p) {
        comment.setDisLikesNum(comment.getDisLikesNum() + p);
        if ((comment.getDisLikesNum() >= Magic.HIDE_STANDARD1) & (comment.getLikesNum() <= Magic.HIDE_STANDARD2 * comment.getDisLikesNum())) {
            comment.setHidden(true);
        } else {
            comment.setHidden(false);
        }
        commentServiceImpl.saveComment(comment);
    }

    /**
     * @param comments 评论集合
     * @return 集合中评论被点赞最多的这个点赞数
     */
    Integer getMaxLikesNum(List<Comment> comments) {
        Integer max = 0;
        for (Comment comment : comments) {
            Integer maxNum = comment.getLikesNum();
            if (maxNum > max) {
                max = maxNum;
            }
        }
        return max;
    }

    /**
     * 取消对评论的点赞
     */
    void deleteCommentLikes(Comment comment, Likes likes, User receiveUser) {
        if (comment.getQuestion() != null) {
            Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByQuestionId(comment.getQuestion().getId()));
            likesServiceImpl.delete(likes);
            dealLikes(receiveUser, comment, comment.getQuestion(), maxNum0, -1, questionServiceImpl);
        }
        //取消点赞前的最高赞数 并处理
        if (comment.getBlog() != null) {
            Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByBlogId(comment.getBlog().getId()));
            likesServiceImpl.delete(likes);
            //对impact属性的处理
            dealLikes(receiveUser, comment, comment.getBlog(), maxNum0, -1, blogServiceImpl);
        }
    }

    /**
     * 处理评论的点赞
     */
    <E extends Template> void dealLikes(User receiveUser, Comment comment, E e, int maxNum0, int p, TemplateServiceImpl<E> repository) {

        comment.setLikesNum(comment.getLikesNum() + p * 1);
        commentServiceImpl.saveComment(comment);

        //问题被（取消）点赞 提问者贡献值+-
        receiveUser.setDonation(receiveUser.getDonation() + p * DonationGrow.COMMENT_APPROVED.getGrow());
        userServiceImpl.saveUser(receiveUser);

        //（取消）点赞后的最高赞数
        Integer maxNum1 = 0;
        if (e instanceof Blog) {
            maxNum1 = getMaxLikesNum(commentServiceImpl.listAllCommentByQuestionId(e.getId()));
        }

        if (e instanceof Question) {
            maxNum1 = getMaxLikesNum(commentServiceImpl.listAllCommentByBlogId(e.getId()));
        }
        e.setImpact(e.getImpact() + p * ImpactGrow.MAX_APPROVED.getGrow() * (maxNum1 - maxNum0));

        repository.save(e);
    }
}
