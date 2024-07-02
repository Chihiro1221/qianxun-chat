package com.haonan.client.handler;

import com.alibaba.fastjson2.JSON;
import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 注册登录类
 *
 * @author wanghaonan
 */
public class RegisterLoginHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.REGISTER_LOGIN;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        try {
            System.out.println("\033[1;34m******* 注意：若未注册，则自动注册 *******\033[0m");
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入用户名：");
            String username = scanner.nextLine();
            System.out.print("请输入密码：");
            String password = scanner.nextLine();
            HashMap<String, String> body = new HashMap<>();
            body.put("username", username);
            body.put("password", password);
            // 发送请求
            HttpUtil.printRequest(out, RouteEnum.REGISTER_LOGIN.getPath(), JSON.toJSONString(body));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求服务端失败！");
        }
    }
}
