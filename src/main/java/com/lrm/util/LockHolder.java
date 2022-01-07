package com.lrm.util;

import java.util.concurrent.locks.ReentrantLock;

public class LockHolder {
    private final static ThreadLocal<ReentrantLock> localLock = new ThreadLocal<>();

    public static void setLocalLock(ReentrantLock lock) {
        localLock.set(lock);
    }

    public static ReentrantLock getLocalLock() {
        return localLock.get();
    }

    public static void clear() {
        localLock.remove();
    }
}
