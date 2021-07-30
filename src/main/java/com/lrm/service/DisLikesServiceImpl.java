package com.lrm.service;

import com.lrm.dao.DisLikesRepository;
import com.lrm.po.Comment;
import com.lrm.po.DisLikes;
import com.lrm.po.Question;
import com.lrm.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class DisLikesServiceImpl implements DisLikesService {
    @Autowired
    DisLikesRepository disLikesRepository;

    /**
     * @param disLikes 要保存的踩
     * @param postUser 发布踩的用户
     * @return 保存的踩
     */
    @Transactional
    @Override
    public DisLikes saveDisLikes(DisLikes disLikes, User postUser) {
        disLikes.setPostUser(postUser);
        return disLikesRepository.save(disLikes);
    }

    @Override
    @Transactional
    public DisLikes saveDisLikes(DisLikes disLikes) {
        return disLikesRepository.save(disLikes);
    }

    @Override
    @Transactional
    public void deleteDisLikes(DisLikes disLikes) {
        disLikesRepository.delete(disLikes);
    }

    /**
     * 查询用户有没有踩过这个问题
     *
     * @param postUser 踩的人
     * @param question 被踩的问题
     * @return 保存的踩
     */
    @Override
    public DisLikes getDisLikes(User postUser, Question question) {
        return disLikesRepository.findByPostUserAndQuestion(postUser, question);
    }

    /**
     * 查询用户有没有踩过这个评论
     *
     * @param postUser 踩的人
     * @param comment  被踩的评论
     * @return 保存的踩
     */
    @Override
    public DisLikes getDisLikes(User postUser, Comment comment) {
        return disLikesRepository.findByPostUserAndComment(postUser, comment);
    }

    @Override
    public DisLikes getDisLikes(Long disLikesId) {
        Optional<DisLikes> disLikes = disLikesRepository.findById(disLikesId);
        return disLikes.orElse(null);
    }

}
