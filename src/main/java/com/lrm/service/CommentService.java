package com.lrm.service;

import com.lrm.po.Comment;
import com.lrm.po.Template;
import com.lrm.po.User;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface CommentService {

    <T extends Template> Comment saveComment(Comment comment, T t, User user);

    Comment saveComment(Comment comment);

    <T extends Template> void deleteComment(Long commentId, T t, TemplateServiceImpl<T> templateService);

    List<Comment> listCommentByQuestionId(Long questionId, Boolean isAnswer);

    List<Comment> listCommentByBlogId(Long blogId, Boolean isAnswer);

    Comment getComment(Long commentId);

    List<Comment> listSelectedAnswerByQuestionId(Long questionId);

    List<Comment> listAllCommentByQuestionId(Long questionId);

    List<Comment> listAllCommentByBlogId(Long blogId);

    List<Comment> listComments(Long userId, Boolean looked);

    List<Comment> listBestComments(Long questionId);

    void listReceivedComments(Comment comment);

}
