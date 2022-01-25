package com.lrm.util;

import com.lrm.po.User;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class UserHolder {
    private final static ThreadLocal<User> localUser = new ThreadLocal<>();

    public static void setLocalUser(User user) {
        localUser.set(user);
    }

    public static User getLocalUser() {
        return localUser.get();
    }

    public static void clear() {
        localUser.remove();
    }
}
