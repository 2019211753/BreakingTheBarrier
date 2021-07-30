package com.lrm.service;

import com.lrm.po.Favorite;
import com.lrm.po.Question;

import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface FavoriteService {
    Favorite saveFavorite(Favorite favorite);

    void deleteFavoriteById(Long favoriteId);

    Favorite updateFavorite(Favorite favorite);

    Favorite addQuestion(Favorite favorite, Question question);

    Favorite removeQuestion(Favorite favorite, Question question);

    List<Favorite> getFavoritesByUserId(Long userId);

    Favorite getFavoriteById(Long favoriteId);

    Favorite getFavoriteByTitle(String title);


}
