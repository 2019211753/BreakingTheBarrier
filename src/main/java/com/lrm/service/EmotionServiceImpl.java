package com.lrm.service;

import com.lrm.dao.EmotionRepository;
import com.lrm.po.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-11
 */
public abstract class EmotionServiceImpl<T extends Emotion>  {

    public abstract EmotionRepository<T> getEmotionRepository();

    /**
     * @param t 要保存的踩/赞
     * @return 保存的踩/赞
     */
    @Transactional
    public T save(T t) {
        EmotionRepository<T> repository = getEmotionRepository();

        return repository.save(t);
    }

    @Transactional
    public void delete(T t) {
        EmotionRepository<T> repository = getEmotionRepository();

        repository.delete(t);
    }

    /**
     * 查询用户有没有踩/赞过这个问题
     *
     * @param postUser 踩/赞的人
     * @param e 被踩/赞的问题/博客
     * @return 保存的踩/赞
     */
    public <E extends Template> T get(User postUser, E e) {
        EmotionRepository<T> repository = getEmotionRepository();

        if (e instanceof Question) {
            return repository.findByPostUserIdAndQuestionId(postUser.getId(), e.getId());
        }

        if (e instanceof Blog) {
            return repository.findByPostUserIdAndBlogId(postUser.getId(), e.getId());
        }
        return null;
    }

    /**
     * 查询用户有没有踩/赞过这个评论
     *
     * @param postUser 踩/赞的人
     * @param comment  被踩/赞的评论
     * @return 保存的踩/赞
     */
    public T get(User postUser, Comment comment) {
        EmotionRepository<T> repository = getEmotionRepository();

        return repository.findByPostUserIdAndComment(postUser.getId(), comment);
    }


    public T get(Long id) {
        EmotionRepository<T> repository = getEmotionRepository();

        Optional<T> t = repository.findById(id);
        return t.orElse(null);
    }
}

