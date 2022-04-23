package com.lrm.service;

import com.lrm.po.Likes;
import com.lrm.po.Template;
import com.lrm.po.User;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface LikesService {
    <E extends Template> Likes save(E e, Likes likes, User postUser, User receiveUser);

    List<Likes> list(Long userId, Boolean looked);

    Integer countUnLooked(Long receiveUserId);

}
