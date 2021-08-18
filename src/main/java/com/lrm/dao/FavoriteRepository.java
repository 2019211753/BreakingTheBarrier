package com.lrm.dao;

import com.lrm.po.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    /**
     * 找到用户的所有收藏夹
     *
     * @param ownerId 收藏夹之主
     * @return 所有收藏夹
     */
    List<Favorite> findAllByOwnerId(Long ownerId);


    /**
     * 查询用户已公开的收藏夹
     *
     * @param open 是否公开
     * @param ownerId 拥有者Id
     * @return 公开/不公开的收藏夹
     */
    List<Favorite> findAllByOpenAndOwnerId(Boolean open, Long ownerId);

    /**
     * 按标题查询用户收藏夹
     *
     * @param title 收藏夹标题
     * @param ownerId 拥有者Id
     * @return 对应收藏夹
     */
    Favorite findByTitleAndOwnerId(String title, Long ownerId);

}
