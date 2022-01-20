package com.lrm.service;

import com.lrm.dao.EmotionRepository;
import com.lrm.dao.LikesRepository;
import com.lrm.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class LikesServiceImpl extends EmotionServiceImpl<Likes> implements LikesService{
    @Autowired
    LikesRepository likesRepository;

    @Override
    public EmotionRepository<Likes> getEmotionRepository() {
        return likesRepository;
    }

    @Override
    @Transactional
    public <E extends Template> Likes save(E e, Likes likes, User postUser, User receiveUser) {
        if (e instanceof Blog) {
            likes.setBlog((Blog) e);
        }
        if (e instanceof Question) {
            likes.setQuestion((Question) e);
        }
        likes.setCreateTime(new Date());
        likes.setPostUser(postUser);
        likes.setReceiveUser(receiveUser);
        likes.setLooked(receiveUser.equals(postUser));
        return likesRepository.save(likes);
    }

    /**
     * 获得未读或已读点赞通知
     *
     * @param userId 当前用户Id
     * @return 未读或已读点赞集合
     */
    @Override
    @Transactional
    public List<Likes> list(Long userId, Boolean looked) {
        return likesRepository.findByReceiveUserIdAndLooked(userId, looked);
    }
}
