package com.haonan.server.controller;

import com.haonan.server.ChatServer;
import com.haonan.server.entity.User;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.HttpUtil;
import com.haonan.server.utils.JDBCUtils;
import com.haonan.server.utils.UserHolder;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 添加好友类
 *
 * @author wanghaonan
 */
public class ApplyForUserController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.APPLY_FOR_FRIENDS;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        try {
            User currentUser = UserHolder.getUser();
            if (currentUser == null) {
                HttpUtil.printResponse(out, 401, "请在登录后操作！");
                return;
            }
            String userName = requestBody.trim();
            String sql = "select * from user where username=?";

            ResultSet resultSet = JDBCUtils.executeResultSetQuery(sql, userName);
            // 用户名不存在
            if (!resultSet.next()) {
                HttpUtil.printResponse(out, 400, "用户不存在！");
                return;
            }

            long userId = resultSet.getLong("id");
            // 用户存在
            sql = "select * from relations where user_id=? and friend_id=?";
            resultSet = JDBCUtils.executeResultSetQuery(sql, currentUser.getId(), userId);
            // 已建立连接
            if (resultSet.next()) {
                HttpUtil.printResponse(out, 400, "已添加过好友或已申请！");
                return;
            }
            // 用户不在线
            if (ChatServer.ONLINE_USERS.get(userName) == null) {
                HttpUtil.printResponse(out, 400, "该用户已离线，请待用户上线之后添加好友！");
                return;
            }
            // 插入关系
            sql = "insert into relations(user_id,friend_id) values(?,?)";
            boolean res = JDBCUtils.executeQuery(sql, currentUser.getId(), userId);
            if (res) {
                sql = "select * from relations where user_id=? and friend_id=?";
                // 查看对方是否已发出好友申请
                resultSet = JDBCUtils.executeResultSetQuery(sql, userId, currentUser.getId());
                if (resultSet.next()) {
                    HttpUtil.printResponse(ChatServer.ONLINE_USERS.get(userName), 200, String.format("与【%s】已成为好友！", currentUser.getUsername()));
                    HttpUtil.printResponse(out, 200, "与【" + userName + "】已成为好友！");
                    return;
                }
                HttpUtil.printResponse(out, 200, "已发出好友申请！");
                // 通知被申请加好友的用户
                HttpUtil.printResponse(ChatServer.ONLINE_USERS.get(userName), 200, String.format("好友申请：来自用户【%s】的好友申请", currentUser.getUsername()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
