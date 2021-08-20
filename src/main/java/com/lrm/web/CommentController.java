package com.lrm.web;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
public class CommentController
{
    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private LikesServiceImpl likesServiceImpl;

    @Autowired
    private DisLikesServiceImpl disLikesServiceImpl;


    /**
     * 展示问题下所有评论
     *
     * @param questionId 评论在哪个问题下 对应的问题Id
     * @param request    用于得到当前userId 处理当前用户点没点过赞的
     * @return 第一类评论、第二类评论
     */
    @GetMapping("/question/{questionId}/comments")
    public Result getQuestionComments(@PathVariable Long questionId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Long userId = TokenInfo.getCustomUserId(request);

        //分别返回两类评论和对应点赞
        List<Comment> comments1 = commentServiceImpl.listCommentByQuestionId(questionId, false);

        hashMap.put("comments1", dealComment(comments1, userId));

        List<Comment> comments2 = commentServiceImpl.listCommentByQuestionId(questionId, true);

        hashMap.put("comments2", dealComment(comments2, userId));

        return new Result(hashMap, "");
    }

    /**
     * 展示博客下所有评论
     */
    @GetMapping("/blog/{blogId}/comments")
    public Result getBlogComments(@PathVariable Long blogId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Long userId = TokenInfo.getCustomUserId(request);

        List<Comment> comments1 = commentServiceImpl.listCommentByBlogId(blogId, false);

        hashMap.put("comments1", dealComment(comments1, userId));

        List<Comment> comments2 = commentServiceImpl.listCommentByBlogId(blogId, true);

        hashMap.put("comments2", dealComment(comments2, userId));

        return new Result(hashMap, "");
    }

    /**
     * @param comments 被处理的comment集合 我更改的只是comment的receiveComment[]属性
     *                 并没有更改他们的父级评论属性 所以仍然可以根据他们的parentComment获取nickname
     * @param userId   当前用户对象 用于处理是否点过赞的
     * @return 给前端的comment集合
     */
    List<Comment> dealComment(List<Comment> comments, Long userId) {
        if (comments.size() != 0) {
            for (Comment comment : comments) {
                insertAttribute(comment, userId);
                List<Comment> receiveComments = comment.getReceiveComments();
                if (receiveComments.size() != 0) {
                    for (Comment receiveComment : receiveComments) {
                        receiveComment.setParentCommentName(receiveComment.getParentComment().getPostUser().getNickname());
                        insertAttribute(receiveComment, userId);
                    }
                }
            }
        }
        return comments;
    }

    /**
     * 配合dealComment插入数据
     *
     * @param comment 被插入的评论对象
     * @param userId  当前用户对象 用于处理是否点过赞的
     */
    void insertAttribute(Comment comment, Long userId) {
        //得到发布问题的人
        User postUser = comment.getPostUser();

        if (likesServiceImpl.get(userServiceImpl.getUser(userId), comment) != null) {
            comment.setApproved(true);
        } else {
            comment.setApproved(false);
        }

        if (disLikesServiceImpl.get(userServiceImpl.getUser(userId), comment) != null) {
            comment.setDisapproved(true);
        } else {
            comment.setDisapproved(false);
        }

        comment.setAvatar(postUser.getAvatar());
        comment.setNickname(postUser.getNickname());
    }


    /**
     * 提交评论到问题
     * 提交表单后 到这里 然后得到id 然后刷新评论
     *
     * @param questionId    对应的问题Id
     * @param comment       前端封装的comment对象
     * @param request       用于得到当前userId 为评论的postUser
     * @param bindingResult 校验异常处理
     * @return 新增的评论或新增失败报错
     */
    @PostMapping("/question/{questionId}/comment/post")
    public Result postQuestionComment(@PathVariable Long questionId, @RequestBody @Valid Comment comment, BindingResult bindingResult, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //得到当前用户
        Long userId = TokenInfo.getCustomUserId(request);
        User postUser = userServiceImpl.getUser(userId);

        Question question = questionServiceImpl.getById(questionId);

        return postComment(comment, bindingResult, postUser, question, questionServiceImpl, hashMap);
    }

    /**
     * 提交评论到博客
     */
    @PostMapping("/blog/{blogId}/comment/post")
    public Result postBlogComment(@PathVariable Long blogId, @RequestBody @Valid Comment comment, BindingResult bindingResult, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //得到当前用户
        Long userId = TokenInfo.getCustomUserId(request);
        User postUser = userServiceImpl.getUser(userId);

        Blog blog = blogServiceImpl.getById(blogId);

        return postComment(comment, bindingResult, postUser, blog, blogServiceImpl, hashMap);
    }

    private <T extends Template> Result postComment(Comment comment, BindingResult bindingResult, User postUser, T t, TemplateServiceImpl<T> templateServiceImpl, Map<String, Object> hashMap) {

        if (postUser.getCanSpeak()) {
            //参数校验
            if (bindingResult.hasErrors()) {
                throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
            }

            //保存
            commentServiceImpl.saveComment(comment, t, postUser);

            //如果有了，更新问题的最新评论时间
            if (commentServiceImpl.getComment(comment.getId()) != null) {
                t.setNewCommentedTime(new Date());
                templateServiceImpl.save(t);

                hashMap.put("comment", comment);

                return new Result(hashMap, "发布成功");
            } else {
                throw new FailedOperationException("发布失败");
            }
        } else {
            throw new NoPermissionException("你无权发布评论");
        }
    }


    /**
     * 删除问题的评论
     *
     * @param questionId 对应问题Id
     * @param commentId 被删除的评论对应的Id
     * @param request   获取要执行删除操作的用户id
     * @return 报错信息
     */
    @GetMapping("/question/{questionId}/comment/{commentId}/delete")
    public Result deleteQuestionComment(@PathVariable Long questionId, @PathVariable Long commentId, HttpServletRequest request) {
        Question question = questionServiceImpl.getById(questionId);
        return deleteComment(commentId, question, questionServiceImpl, request);
    }

    /**
     * 删除博客的评论
     */
    @GetMapping("/blog/{blogId}/comment/{commentId}/delete")
    public Result deleteBlogComment(@PathVariable Long blogId, @PathVariable Long commentId, HttpServletRequest request) {
        Blog blog = blogServiceImpl.getById(blogId);
        return deleteComment(commentId, blog, blogServiceImpl, request);
    }

    private <T extends Template> Result deleteComment(Long commentId, T t, TemplateServiceImpl<T> templateServiceImpl, HttpServletRequest request) {
        //得到当前用户 以判断权限
        User customUser = userServiceImpl.getUser(TokenInfo.getCustomUserId(request));
        Boolean admin = TokenInfo.isAdmin(request);

        Comment comment = commentServiceImpl.getComment(commentId);

        //如果评论不存在&没权限删除评论报错
        if (comment == null) {
            throw new NotFoundException("未查询到该评论");
        }
        if ((comment.getPostUser() != customUser) & (!admin)) {
            throw new NoPermissionException("你无权删除该评论");
        }

        commentServiceImpl.deleteComment(commentId, t, templateServiceImpl);
        comment = commentServiceImpl.getComment(commentId);

        if (comment != null) {
            throw new FailedOperationException("删除失败");
        } else {
            return new Result(null, "删除成功");
        }
    }


}