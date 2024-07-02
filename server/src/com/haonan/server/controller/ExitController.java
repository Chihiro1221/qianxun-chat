package com.haonan.server.controller;

import com.haonan.server.ChatServer;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.HttpUtil;
import com.haonan.server.utils.UserHolder;

import java.io.PrintWriter;

/**
 * 退出系统类
 *
 * @author wanghaonan
 */
public class ExitController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.EXIT;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        String user = UserHolder.getUser().getUsername();
        ChatServer.ONLINE_USERS.remove(user);
        UserHolder.removeUser();
        HttpUtil.printResponse(out, 200, "连接关闭");
        ChatServer.ONLINE_USERS.forEach((userName, userOut) -> {
            HttpUtil.printResponse(userOut, 200, "用户" + user + "已下线！");
        });
    }
}
