package com.lrm.service;

import com.lrm.dao.FavoriteRepository;
import com.lrm.po.Favorite;
import com.lrm.po.Question;
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
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;


    @Transactional
    @Override
    public Favorite saveFavorite(Favorite favorite) {
        favorite.setCreateTime(new Date());
        favorite.setUpdateTime(new Date());
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteById(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    @Transactional
    @Override
    public Favorite addQuestion(Favorite favorite, Question question) {
        favorite.getFavoriteQuestions().add(question);
        favorite.setUpdateTime(new Date());
        favorite.setSize(favorite.getSize() + 1);
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite removeQuestion(Favorite favorite, Question question) {
        favorite.getFavoriteQuestions().remove(question);
        favorite.setUpdateTime(new Date());
        favorite.setSize(favorite.getSize() - 1);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    @Override
    public Favorite updateFavorite(Favorite favorite) {
        favorite.setUpdateTime(new Date());
        return favoriteRepository.save(favorite);
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

    @Override
    public Favorite getFavoriteByTitle(String title) {
        return favoriteRepository.findByTitle(title);
    }


}
