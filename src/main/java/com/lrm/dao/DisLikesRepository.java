package com.lrm.dao;

import com.lrm.po.Comment;
import com.lrm.po.DisLikes;
import com.lrm.po.Question;
import com.lrm.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface DisLikesRepository extends JpaRepository<DisLikes, Long> {
    /**
     * 查询User有没有对该问题点过踩
     *
     * @param postUser 当前用户
     * @param question 当前问题
     * @return 没点过返回null
     */
    DisLikes findByPostUserAndQuestion(User postUser, Question question);

    /**
     * 查询User有没有对该评论点过踩
     *
     * @param postUser 当前用户
     * @param comment  当前评论
     * @return 没点过返回null
     */
    DisLikes findByPostUserAndComment(User postUser, Comment comment);
}
