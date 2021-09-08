package com.lrm.service;

import com.lrm.po.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public interface UserService {

    User checkRegister(String username, String nickname);

    User saveUser(String username, String password, String nickname);

    User saveUser(User user);

    User checkUser(String username, String password);

    User updateUser(User user) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    User getUser(Long userId);

    User getUser(String nickname);

    Long getRank(Integer donation);

    Long countUser();

    List<User> listTopUsers(int size);
}
