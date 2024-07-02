package com.haonan.client.handler;

import com.haonan.client.ChatClient;
import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 添加好友类
 *
 * @author wanghaonan
 */
public class ApplyForUserHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.APPLY_FOR_FRIENDS;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            System.out.println("请输入你想要添加的用户：");
            String userName = scanner.nextLine();
            HttpUtil.printRequest(out, RouteEnum.APPLY_FOR_FRIENDS.getPath(), userName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //scanner.close();
        }
    }
}
