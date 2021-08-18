package com.lrm.web;

import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Magic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public void approveQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

       approve(question, questionServiceImpl, request);
    }

    /**
     * 点赞/取消赞博客
     *
     * @param request    获得执行赞动作的用户id
     * @param blogId 博客Id
     */
    @GetMapping("/blog/{blogId}/approve")
    public void approveBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        approve(blog, blogServiceImpl, request);
    }

    public <E extends Template> void approve(E e, TemplateServiceImpl<E> repository, HttpServletRequest request) {
        Long postUserId = TokenInfo.getCustomUserId(request);

        //得到消息接受双方
        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = e.getUser();

        //若点过踩 取消点踩
        DisLikes dislikes = dislikesServiceImpl.get(postUser, e);
        if (dislikes != null) {
            dislikesServiceImpl.delete(dislikes);

            hide(e, -1, repository);
        }

        //如果存在点赞对象 就删除 即取消点赞 否则点赞
        Likes likes = likesServiceImpl.get(postUser, e);
        if (likes != null) {
            likesServiceImpl.delete(likes);

            dealLikes(e, receiveUser, -1, repository);
        } else {
            Likes likes1 = new Likes();

            likesServiceImpl.save(e, likes1, postUser, receiveUser);

            //处理部分属性
            dealLikes(e, receiveUser, 1, repository);
        }
    }


    /**
     * 点踩/取消踩问题
     *
     * @param request    获取执行踩动作的用户id
     * @param questionId 问题Id
     */
    @GetMapping("/question/{questionId}/disapprove")
    public void disapproveQuestion(@PathVariable Long questionId, HttpServletRequest request)
    {
        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

       disapprove(question, questionServiceImpl, request);
    }

    /**
     * 点踩/取消踩博客
     *
     * @param request    获取执行踩动作的用户id
     * @param blogId 博客Id
     */
    @GetMapping("/blog/{blogId}/disapprove")
    public void disapproveBlog(@PathVariable Long blogId, HttpServletRequest request)
    {
        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        disapprove(blog, blogServiceImpl, request);
    }

    <E extends Template> void disapprove(E e, TemplateServiceImpl<E> repository, HttpServletRequest request) {
        Long postUserId = TokenInfo.getCustomUserId(request);

        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = e.getUser();

        //若点过赞 取消点赞 贡献值复原
        Likes likes = likesServiceImpl.get(postUser, e);
        if (likes != null) {
            likesServiceImpl.delete(likes);
            dealLikes(e, receiveUser, -1, repository);
        }

        //若点过踩 取消点踩 否则点踩
        DisLikes dislikes = dislikesServiceImpl.get(postUser, e);
        if (dislikes != null) {
            dislikesServiceImpl.delete(dislikes);

            hide(e, -1, repository);
        } else {
            DisLikes dislikes1 = new DisLikes();

            dislikesServiceImpl.save(e, dislikes1, postUser);
            hide(e, 1, repository);
        }
    }


    /**
     * 给评论点赞
     *
     * @param commentId 被点赞的评论Id
     * @param request 获取执行点赞动作的用户Id
     */
    @GetMapping("comment/{commentId}/approve")
    public void approve(@PathVariable Long commentId, HttpServletRequest request) {
        Comment comment = commentServiceImpl.getComment(commentId);

        //只能给有效问题点赞
        if (comment.getAnswer()) {

            //当前用户 点赞的人
            Long postUserId = TokenInfo.getCustomUserId(request);
            User postUser = userServiceImpl.getUser(postUserId);
            //被点赞的人
            User receiveUser = comment.getReceiveUser();

            //若点过踩 取消点踩
            DisLikes disLikes = dislikesServiceImpl.get(postUser, comment);
            if (disLikes != null) {
                dislikesServiceImpl.delete(disLikes);
                //复原
                hideComment(comment, -1);
            }

            Likes likes = likesServiceImpl.get(postUser, comment);

            //点过赞删除，无则增加
            if (likes != null) {
                //取消点赞前的最高赞数 并处理
                deleteCommentLikes(comment, likes, receiveUser);
            } else {
                Likes likes1 = new Likes();

                likes1.setComment(comment);

                if (comment.getQuestion() != null) {
                    Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByQuestionId(comment.getQuestion().getId()));
                    likesServiceImpl.save(likes1);
                    dealLikes(receiveUser, comment, comment.getQuestion(), maxNum0, -1, questionServiceImpl);
                }
                //取消点赞前的最高赞数 并处理
                if (comment.getBlog() != null) {
                    Integer maxNum0 = getMaxLikesNum(commentServiceImpl.listAllCommentByBlogId(comment.getBlog().getId()));
                    likesServiceImpl.save(likes1);
                    //对impact属性的处理
                    dealLikes(receiveUser, comment, comment.getBlog(), maxNum0, -1, blogServiceImpl);
                }
            }
        }
    }


    /**
     * 点踩 到标准就隐藏
     *
     * @param request 获取执行点踩动作的用户Id
     * @param commentId 评论Id
     */
    @GetMapping("comment/{commentId}/disapprove")
    public void disapprove(@PathVariable Long commentId, HttpServletRequest request) {
        Comment comment = commentServiceImpl.getComment(commentId);

        Long postUserId = TokenInfo.getCustomUserId(request);
        User postUser = userServiceImpl.getUser(postUserId);
        User receiveUser = comment.getReceiveUser();

        //若点过赞 取消点赞 贡献值复原
        Likes likes = likesServiceImpl.get(postUser, comment);
        if (likes != null) {
            deleteCommentLikes(comment, likes, receiveUser);
        }

        //若点过踩 取消点踩 否则点踩
        DisLikes disLikes = dislikesServiceImpl.get(postUser, comment);
        if (disLikes != null) {
            dislikesServiceImpl.delete(disLikes);

            hideComment(comment, -1);
        } else {
            DisLikes disLikes1 = new DisLikes();

            disLikes1.setPostUser(postUser);
            disLikes1.setComment(comment);
            dislikesServiceImpl.save(disLikes1);

            hideComment(comment, 1);
        }
    }

    <E extends Template> void dealLikes(E e, User receiveUser, int p, TemplateServiceImpl<E> repository) {
        //不明白为什么把他们放在saveLikes之前就可以update
        e.setLikesNum(e.getLikesNum() + p * 1);

        //提问者贡献值对问题影响力+-8 (取消）点赞本身+-2
        e.setImpact(e.getImpact() + p * 2 + p * 8);

        //问题被（取消）点赞 提问者贡献值+-2
        receiveUser.setDonation(receiveUser.getDonation() + p * 2);

        repository.save(e);
        userServiceImpl.saveUser(receiveUser);
    }

    <E extends Template> void hide(E e, int p, TemplateServiceImpl<E> repository) {
        e.setDisLikesNum(e.getDisLikesNum() + p);

        //被踩到一定程度隐藏
        if ((e.getDisLikesNum() >= Magic.HIDE_STANDARD1) & (e.getLikesNum() <= Magic.HIDE_STANDARD2 * e.getDisLikesNum())) {
            e.setHidden(true);
        } else {
            e.setHidden(false);
        }
        repository.save(e);
    }

    void hideComment(Comment comment, int p) {
        comment.setDisLikesNum(comment.getDisLikesNum() + p);
        //被踩到一定程度隐藏评论
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

    <E extends Template> void dealLikes(User receiveUser, Comment comment, E e, int maxNum0, int p, TemplateServiceImpl<E> repository) {

        comment.setLikesNum(comment.getLikesNum() + p * 1);
        commentServiceImpl.saveComment(comment);

        //问题被（取消）点赞 提问者贡献值+-3
        receiveUser.setDonation(receiveUser.getDonation() + p * 3);
        userServiceImpl.saveUser(receiveUser);

        //提问者贡献值对问题影响力+-12
        //（取消）点赞后的最高赞数
        Integer maxNum1 = 0;
        if (e instanceof Blog) {
            maxNum1 = getMaxLikesNum(commentServiceImpl.listAllCommentByQuestionId(e.getId()));
        }

        if (e instanceof Question) {
            maxNum1 = getMaxLikesNum(commentServiceImpl.listAllCommentByBlogId(e.getId()));
        }
        e.setImpact(e.getImpact() + p * 2 * (maxNum1 - maxNum0) + p * 12);

        repository.save(e);
    }
}
