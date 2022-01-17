package com.lrm.dao;

import com.lrm.po.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 查询问题下的精选回答
     *
     * @param questionId 问题Id
     * @param sort 排序方式
     * @return
     */
    @Query("select c from #{#entityName} c where c.question.id = ?1 and c.answer = true and c.selected = true")
    List<Comment> findSelectedAnswerByQuestionId(Long questionId, Sort sort);


    /**
     * 查询问题下的第一级评论
     *
     * @param questionId 问题id
     * @param sort       排序顺序
     * @param answer     哪类回答
     * @return 评论集合
     */
    @Query("select c from #{#entityName} c where c.parentComment.id is null and c.question.id = ?1 and c.answer = ?2")
    List<Comment> findByQuestionIdAndAnswer(Long questionId, Boolean answer, Sort sort);

    //原本是这样的 会报错无法识别Answer 不过当时是用的isAnswer 似乎POJO属性不能是isxxx
    //List<Comment> findByQuestionIdAndParentCommentNullAndAnswer(Long questionId, Sort sort, Boolean answer);

    /**
     * 查询博客下的第一级评论
     *
     * @param blogId 博客id
     * @param sort       排序顺序
     * @param answer     哪类回答
     * @return 评论集合
     */
    @Query("select c from #{#entityName} c where c.parentComment.id is null and c.blog.id = ?1 and c.answer = ?2")
    List<Comment> findByBlogIdAndAnswer(Long blogId, Boolean answer, Sort sort);

    /**
     * 得到问题对应的所有评论
     *
     * @param questionId 问题对应id
     * @return 评论集合
     */
    List<Comment> findByQuestionId(Long questionId);

    /**
     * 得到博客对应的所有评论
     *
     * @param blogId 博客对应Id
     * @return 评论集合
     */
    List<Comment> findByBlogId(Long blogId);

    /**
     * 查询用户未读评论
     *
     * @param receiveUserId 用户Id
     * @param isLooked      是否已读
     * @return 评论集合
     */
    List<Comment> findByReceiveUserIdAndLooked(Long receiveUserId, Boolean isLooked);

    /**
     * 查询最高赞数的n个答案
     */
    @Query("select c from #{#entityName} c where c.question.id = ?1 and c.answer = true and c.selected = false")
    List<Comment> findTopByQuestionIdAndAnswer(Pageable pageable, Long questionId);


}
