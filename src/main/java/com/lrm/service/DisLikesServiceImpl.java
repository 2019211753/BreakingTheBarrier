package com.lrm.service;

import com.lrm.dao.DisLikesRepository;
import com.lrm.dao.EmotionRepository;
import com.lrm.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class DisLikesServiceImpl extends EmotionServiceImpl<DisLikes> implements DisLikesService {
    @Autowired
    DisLikesRepository disLikesRepository;

    @Override
    public EmotionRepository<DisLikes> getEmotionRepository() {
        return disLikesRepository;
    }

    @Override
    @Transactional
    public <E extends Template> DisLikes save(E e, DisLikes disLikes, User postUser) {
        if (e instanceof Question) {
            disLikes.setQuestion((Question) e);
        }
        if (e instanceof Blog) {
            disLikes.setBlog((Blog) e);
        }

        disLikes.setPostUser(postUser);
        return disLikesRepository.save(disLikes);
    }
}
