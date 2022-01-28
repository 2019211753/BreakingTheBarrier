package com.lrm.service;

import com.lrm.dao.FavoriteRepository;
import com.lrm.po.Blog;
import com.lrm.po.Favorite;
import com.lrm.po.Question;
import com.lrm.po.Template;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;


    @Override
    @Transactional
    public Favorite saveFavorite(Favorite favorite) {
        favorite.setCreateTime(new Date());
        favorite.setUpdateTime(new Date());
        favorite.setSize(0);
        return favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void deleteFavoriteById(Long favoriteId) {
        Favorite favorite = getFavoriteById(favoriteId);
        Iterator<Question> it1 = favorite.getFavoriteQuestions().iterator();
        while (it1.hasNext()) {
             Question q = it1.next();
             q.getFavorites().remove(favorite);
        }
        Iterator<Blog> it2 = favorite.getFavoriteBlogs().iterator();
        while (it2.hasNext()) {
            Blog b = it2.next();
            b.getFavorites().remove(favorite);
        }
        favoriteRepository.deleteById(favoriteId);
    }

    /**
     * 将博客/问题加入收藏夹
     *
     * @param favorite 收藏夹
     * @return 新收藏夹
     */
    @Transactional
    @Override
    public <T extends Template> Favorite add(Favorite favorite, T t) {
        t.getFavorites().add(favorite);
        t.setCollectedNum(t.getCollectedNum() + 1);

        favorite.setUpdateTime(new Date());
        favorite.setSize(favorite.getSize() + 1);
        return favoriteRepository.save(favorite);
    }

    /**
     * @param favorite 收藏夹
     * @return 新收藏夹
     */
    @Override
    @Transactional
    public <T extends Template> Favorite remove(Favorite favorite, T t) {
        t.getFavorites().remove(favorite);
        t.setCollectedNum(t.getCollectedNum() - 1);
        favorite.setUpdateTime(new Date());
        favorite.setSize(favorite.getSize() - 1);
        return favoriteRepository.save(favorite);
    }



    /**
     * @param favorite 新收藏夹
     * @param f 旧收藏夹
     * @return 新收藏夹
     */
    @Override
    @Transactional
    public Favorite updateFavorite(Favorite favorite, Favorite f) {
        MyBeanUtils.copy(Favorite.class, favorite, f);
        f.setUpdateTime(new Date());
        return favoriteRepository.save(f);
    }

    /**
     * 查找某用户所有的收藏夹
     *
     * @param userId 被查找的用户Id
     * @return 收藏夹集合
     */
    @Override
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findAllByOwnerId(userId);
    }

    @Override
    public Favorite getFavoriteById(Long favoriteId) {
        Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);
        return favorite.orElse(null);
    }

    /**
     * @param title 收藏夹标题
     * @param userId 用户Id
     * @return 该用户的title标题对应的收藏夹
     */
    @Override
    public Favorite getFavoriteByTitle(String title, Long userId) {
        return favoriteRepository.findByTitleAndOwnerId(title, userId);
    }

    /**
     * @param open 公开么
     * @param userId 用户Id
     * @return 用户公开的收藏夹
     */
    @Override
    public List<Favorite> getFavorites(Boolean open, Long userId) {
        return favoriteRepository.findAllByOpenAndOwnerId(open, userId);
    }
}
