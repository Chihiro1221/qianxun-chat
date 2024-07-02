package com.haonan.server.controller;

import com.alibaba.fastjson2.JSON;
import com.haonan.server.ChatServer;
import com.haonan.server.entity.User;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.utils.HttpUtil;
import com.haonan.server.utils.UserHolder;

import java.io.PrintWriter;
import java.util.Map;

/**
 * 开启聊天类
 *
 * @author wanghaonan
 */
public class BeginCommunicateController implements RouteMapping {

    @Override
    public RouteEnum getRouteEnum() {
        return RouteEnum.COMMUNICATION;
    }

    @Override
    public void handle(String requestBody, PrintWriter out) {
        User currentUser = UserHolder.getUser();
        if (currentUser == null) {
            HttpUtil.printResponse(out, 401, "请在登录后操作！");
            return;
        }
        Map<String, String> body = JSON.parseObject(requestBody, Map.class);
        String toUser = body.get("to_user");
        String message = body.get("message");
        PrintWriter printWriter = ChatServer.ONLINE_USERS.get(toUser);
        if (printWriter == null) {
            HttpUtil.printResponse(out, 200, "该用户已下线或不存在！请检查用户名和当前在线用户");
            return;
        }

        HttpUtil.printResponse(printWriter, 200, "\033[1;34m来自好友【" + currentUser.getUsername() + "】的消息：\033[0m" + message);
    }
}
