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

/**
 * 显示当前用户所有的好友
 *
 * @author wanghaonan
 */
public class ShowAllFriendController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.SHOW_ALL_FRIENDS;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        try {
            User currentUser = UserHolder.getUser();
            if (currentUser == null) {
                HttpUtil.printResponse(out, 401, "请在登录后操作！");
                return;
            }
            // 查询当前用户的好友
            String sql = sql = "select rels.*,user.username from (select * from relations where user_id = ? and friend_id in (\n" +
                    "\tselect user_id from relations where friend_id = ?\n" +
                    ")) as rels, user where user.id=rels.friend_id;";
            ResultSet resultSet = JDBCUtils.executeResultSetQuery(sql, currentUser.getId(), currentUser.getId());
            StringBuilder response = new StringBuilder();
            int cnt = 0;
            while (resultSet.next()) {
                ++cnt;
                String friendName = resultSet.getString("username");
                if (ChatServer.ONLINE_USERS.get(friendName) != null) {
                    // 在线用户，浅绿色
                    response.append("\033[0;32m").append(friendName).append("\033[0m").append("\n");
                } else {
                    // 离线用户，灰色
                    response.append("\033[0;37m").append(friendName).append("\033[0m").append("\n");
                }
            }
            response.insert(0, "您共有" + cnt + "名好友，\033[0;32m绿色\033[0m表示在线，\033[0;37m灰色\033[0m表示离线，只有\033[1;31m在线用户\033[0m才可聊天！\n");
            response.append("请输入\033[1;31m好友名称\033[0m开启聊天（输入#退出）：\n");
            HttpUtil.printResponse(out, 200, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
