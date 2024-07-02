package com.haonan.server.controller;

import com.alibaba.fastjson2.JSON;
import com.haonan.server.ChatServer;
import com.haonan.server.entity.User;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.*;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 注册登录类
 *
 * @author wanghaonan
 */
public class RegisterLoginController implements RouteMapping {
    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.REGISTER_LOGIN;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        User currentUser = UserHolder.getUser();
        // 已经登陆过
        if (currentUser != null) {
            // 清除之前登录的记录
            UserHolder.removeUser();
            ChatServer.ONLINE_USERS.remove(currentUser.getUsername());
        }
        User user = JSON.parseObject(requestBody, User.class);
        // 不为空
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            HttpUtil.printResponse(out, 200, "参数不合法！");
            return;
        }
        String sql = "select * from user where username=? and password=?";
        ResultSet resultSet = JDBCUtils.executeResultSetQuery(sql, user.getUsername(), DigestUtils.getMd5(user.getPassword()));
        try {
            // 用户名已存在的情况
            if (resultSet.next()) {
                HttpUtil.printResponse(out, 200, "登录成功！");
                user.setId(resultSet.getLong("id"));
                JDBCUtils.closeStatement(resultSet);
            } else {
                // 若是用户名已存在而密码不正确则提示账号或密码错误，如果不存在则直接注册
                sql = "select * from user where username=?";
                resultSet = JDBCUtils.executeResultSetQuery(sql, user.getUsername());
                if (resultSet.next()) {
                    HttpUtil.printResponse(out, 400, "密码错误！");
                    JDBCUtils.closeStatement(resultSet);
                    return;
                }
                // 不存在则执行插入，并登录
                sql = "insert into user(username,password) values(?,?)";
                boolean res = JDBCUtils.executeQuery(sql, user.getUsername(), DigestUtils.getMd5(user.getPassword()));
                if (res) {
                    HttpUtil.printResponse(out, 200, "注册成功，已自动登录！");
                    sql = "select * from user where username=?";
                    resultSet = JDBCUtils.executeResultSetQuery(sql, user.getUsername());
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong("id"));
                        JDBCUtils.closeStatement(resultSet);
                    }
                }
            }
            // 将当前用户信息保存到指定线程变量中
            UserHolder.setUser(user);
            // 将用户登录状态（包括对应的流）保存起来
            ChatServer.ONLINE_USERS.put(user.getUsername(), out);
            // 通知所有在线用户
            ChatServer.ONLINE_USERS.forEach((userName, userOut) -> {
                if (!userName.equals(user.getUsername())) {
                    HttpUtil.printResponse(userOut, 201, String.format("用户【%s】上线啦！", user.getUsername()));
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            LoggerUtils.logError("SQL查询异常！");
        }
    }
}
