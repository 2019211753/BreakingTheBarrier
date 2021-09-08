package com.lrm.service;

import com.lrm.po.Favorite;
import com.lrm.po.Template;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface FavoriteService {
    Favorite saveFavorite(Favorite favorite);

    void deleteFavoriteById(Long favoriteId);

    Favorite updateFavorite(Favorite favorite, Favorite f) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    <T extends Template> Favorite add(Favorite favorite, T t);

    <T extends Template> Favorite remove(Favorite favorite, T t);

    List<Favorite> getFavoritesByUserId(Long userId);

    List<Favorite> getFavorites(Boolean open, Long userId);

    Favorite getFavoriteById(Long favoriteId);

    Favorite getFavoriteByTitle(String title, Long userId);


}
