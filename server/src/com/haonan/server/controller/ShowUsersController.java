package com.haonan.server.controller;

import com.haonan.server.ChatServer;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.HttpUtil;

import java.io.PrintWriter;

/**
 * 展示用户信息类
 *
 * @author wanghaonan
 */
public class ShowUsersController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.SHOW_USERS;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        StringBuilder response = new StringBuilder();
        response.append("当前在线人数：").append(ChatServer.ONLINE_USERS.size()).append("\n");
        ChatServer.ONLINE_USERS.forEach((userName, curOut) -> response.append(userName).append("\n"));
        HttpUtil.printResponse(out, 200, response.toString());
    }
}
