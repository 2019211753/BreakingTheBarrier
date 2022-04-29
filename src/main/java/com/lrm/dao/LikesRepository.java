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
     * 可考虑对发布时间限制 AndCreateTimeAfter
     *
     * @param receiveUserId 当前用户ID
     * @param isLooked      是否已读
     * @return 点赞记录集合
     */
    List<Likes> findByReceiveUserIdAndLooked(Long receiveUserId, Boolean isLooked);

    /**
     * 查询某用户当前的未读点赞数量
     *
     * @param receiveUserId 收信者id
     * @return 数量
     */
    Integer countAllByLookedFalseAndReceiveUserId(Long receiveUserId);
}
