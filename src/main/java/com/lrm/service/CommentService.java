package com.lrm.service;

import com.lrm.po.Comment;
import com.lrm.po.User;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface CommentService {

    Comment saveComment(Comment comment, Long questionId, User user);

    Comment saveComment(Comment comment);

    List<Comment> listCommentByQuestionId(Long questionId, Boolean isAnswer);

    Comment getComment(Long commentId);

    List<Comment> listAllCommentByQuestionId(Long questionId);

    List<Comment> listComments(Long userId, Boolean looked);

    void deleteComment(Long commentId);

}
