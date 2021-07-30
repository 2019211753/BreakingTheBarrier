package com.lrm.service;

import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.Question;
import com.lrm.po.User;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface LikesService {
    Likes saveLikes(Likes likes, User postUser, User receiveUser);

    Likes saveLikes(Likes likes);

    void deleteLikes(Likes likes);

    Likes getLikes(User postUser, Question question);

    Likes getLikes(User postUser, Comment comment);

    Likes getLikes(Long likesId);

    List<Likes> listLikes(Long userId, Boolean looked);


}
