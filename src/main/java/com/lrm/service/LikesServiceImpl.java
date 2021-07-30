package com.lrm.service;

import com.lrm.dao.LikesRepository;
import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.Question;
import com.lrm.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class LikesServiceImpl implements LikesService{
    @Autowired
    LikesRepository likesRepository;

    @Transactional
    @Override
    public Likes saveLikes(Likes likes, User postUser, User receiveUser) {
        likes.setCreateTime(new Date());
        likes.setPostUser(postUser);
        likes.setReceiveUser(receiveUser);
        likes.setLooked(receiveUser == postUser);
        likes.setPostUserId0(postUser.getId());

        if (likes.getLikeComment()) {
            if (likes.getComment().getPostUser().equals(likes.getPostUser())) {
                likes.setLooked(true);
            }
            likes.setQuestionId0(likes.getComment().getQuestionId0());
        }

        if (likes.getLikeQuestion()) {
            if (likes.getQuestion().getUser().equals(likes.getPostUser())) {
                likes.setLooked(true);
            }
            likes.setQuestionId0(likes.getQuestion().getId());
        }
        return likesRepository.save(likes);
    }

    @Override
    @Transactional
    public Likes saveLikes(Likes likes) {
        return likesRepository.save(likes);
    }

    @Override
    @Transactional
    public void deleteLikes(Likes likes) {
        likesRepository.delete(likes);
    }

    @Override
    public Likes getLikes(User postUser, Question question) {
        return likesRepository.findByPostUserAndQuestion(postUser, question);
    }

    @Override
    public Likes getLikes(User postUser, Comment comment) {
        return likesRepository.findByPostUserAndComment(postUser, comment);
    }

    @Override
    public Likes getLikes(Long likesId) {
        Optional<Likes> likes = likesRepository.findById(likesId);
        return likes.orElse(null);
    }

    /**
     * 获得未读或已读点赞通知
     *
     * @param userId 当前用户Id
     * @return 未读或已读点赞集合
     */
    @Override
    public List<Likes> listLikes(Long userId, Boolean looked) {
        return likesRepository.findByReceiveUserIdAndLooked(userId, looked);
    }
}
