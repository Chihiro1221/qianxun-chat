package com.haonan.client.handler;

import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 展示用户信息类
 *
 * @author wanghaonan
 */
public class ShowUsersHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.SHOW_USERS;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        try {
            // 发送请求
            HttpUtil.printRequest(out, RouteEnum.SHOW_USERS.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求服务端失败！");
        }
    }
}
