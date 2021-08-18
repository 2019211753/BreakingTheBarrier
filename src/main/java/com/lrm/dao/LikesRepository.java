package com.lrm.dao;

import com.lrm.po.Likes;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface LikesRepository extends EmotionRepository<Likes> {
    /**
     * 已/未读的点赞记录
     *
     * @param receiveUserId 当前用户ID
     * @param isLooked      是否已读
     * @return 点赞记录集合
     */
    List<Likes> findByReceiveUserIdAndLooked(Long receiveUserId, Boolean isLooked);

}
