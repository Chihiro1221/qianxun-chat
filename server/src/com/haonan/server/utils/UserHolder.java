package com.haonan.server.utils;

import com.haonan.server.entity.User;

import java.io.PrintWriter;

/**
 * 线程变量工具类
 *
 * @author wanghaonan
 */
public class UserHolder {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }
}
