package com.haonan.server.controller;

import com.haonan.server.ChatServer;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.HttpUtil;
import com.haonan.server.utils.JDBCUtils;
import com.haonan.server.utils.UserHolder;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 删除好友类
 *
 * @author wanghaonan
 */
public class RemoveFriendController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.REMOVE_FRIENDS;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        try {
            String userName = requestBody.trim();
            String sql = "select * from user where username = ?";
            ResultSet resultSet = JDBCUtils.executeResultSetQuery(sql, userName);
            if (resultSet.next()) {
                // 获取申请人的id
                long userId = resultSet.getLong("id");
                Long currentUserId = UserHolder.getUser().getId();
                JDBCUtils.closeStatement(resultSet);
                // 将双向记录删除
                sql = "delete from relations where user_id = ? and friend_id = ?";
                boolean res1 = JDBCUtils.executeQuery(sql, userId, currentUserId);
                boolean res2 = JDBCUtils.executeQuery(sql, currentUserId, userId);
                // 如果第一条执行成功而第二条没有，则表示只是userName单向申请了好友，而当前用户拒绝
                if (res1 && !res2) {
                    PrintWriter printWriter = ChatServer.ONLINE_USERS.get(userName);
                    HttpUtil.printResponse(printWriter, 200, "用户【" + UserHolder.getUser().getUsername() + "】已拒绝您的好友申请！");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
