package com.lrm.dao;

import com.lrm.po.Comment;
import com.lrm.po.Emotion;
import com.lrm.po.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-11
 */
@NoRepositoryBean
public interface EmotionRepository<T extends Emotion> extends JpaRepository<T, Long> {
    /**
     * 查询User有没有对该博客点过踩/赞
     *
     * @param postUserId 当前用户Id
     * @param blogId 要查询的博客
     * @param <E> 点赞还是点踩
     * @return 没点过返回null
     */
    @Query("select e from #{#entityName} e where e.comment.id is null and e.postUser.id = ?1 and e.blog.id = ?2")
    <E extends Template> T findByPostUserIdAndBlogId(Long postUserId, Long blogId);

    /**
     * 查询User有没有对该问题点过踩/赞
     *
     * @param postUserId 当前用户Id
     * @param questionId 要查询的问题
     * @param <E> 点赞还是点踩
     * @return 没点过返回null
     */
    @Query("select e from #{#entityName} e where e.comment.id is null and e.postUser.id = ?1 and e.question.id = ?2")
    <E extends Template> T findByPostUserIdAndQuestionId(Long postUserId, Long questionId);

    /**
     * 查询User有没有对该评论点过踩/赞
     *
     * @param postUserId 当前用户Id
     * @param comment  当前评论
     * @return 没点过返回null
     */
    T findByPostUserIdAndComment(Long postUserId, Comment comment);

}
