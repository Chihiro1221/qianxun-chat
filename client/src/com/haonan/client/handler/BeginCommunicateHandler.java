package com.haonan.client.handler;

import com.alibaba.fastjson2.JSON;
import com.haonan.client.ChatClient;
import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 开启聊天类
 *
 * @author wanghaonan
 */
public class BeginCommunicateHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.COMMUNICATION;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        HttpUtil.printRequest(out, RouteEnum.SHOW_ALL_FRIENDS.getPath());
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        if (userName.trim().equals("#")) return;
        System.out.println("已开启聊天，请输入聊天内容（输入#结束）：");
        String message = "";
        while (true) {
            HashMap<String, String> body = new HashMap<>();
            message = scanner.nextLine();
            if (message.equals("#")) break;
            if (message.trim().isEmpty()) continue;

            body.put("to_user", userName);
            body.put("message", message);
            HttpUtil.printRequest(out, RouteEnum.COMMUNICATION.getPath(), JSON.toJSONString(body));
        }
    }
}
