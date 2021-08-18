package com.lrm.service;

import com.lrm.po.DisLikes;
import com.lrm.po.Template;
import com.lrm.po.User;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface DisLikesService {

    <E extends Template> DisLikes save(E e, DisLikes disLikes, User postUser);
}
