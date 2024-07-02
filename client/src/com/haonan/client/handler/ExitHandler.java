package com.haonan.client.handler;

import com.haonan.client.ChatClient;
import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 退出系统类
 *
 * @author wanghaonan
 */
public class ExitHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.EXIT;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        try {
            HttpUtil.printRequest(out, RouteEnum.EXIT.getPath(), "close");
            ChatClient.setRunning(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("流关闭失败！");
        }
    }
}
