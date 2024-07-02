package com.haonan.client.handler;

import com.haonan.client.ChatClient;
import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.service.OperatorType;
import com.haonan.server.enums.RouteEnum;
import com.haonan.server.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 处理好友申请类
 *
 * @author wanghaonan
 */
public class DealWithApplyForHandler implements OperatorType {
    @Override
    public OperatorEnum getOperatorTypeEnum() {
        return OperatorEnum.DEAL_WITH_APPLY_FOR;
    }

    @Override
    public void handle(BufferedReader in, PrintWriter out) {
        if (ChatClient.messageQueue.isEmpty()) {
            System.out.println("暂无好友请求！");
            return;
        }
        while (ChatClient.messageQueue.size() > 0) {
            try {
                System.out.println("共有【" + ChatClient.messageQueue.size() + "】条好友申请");
                String userName = ChatClient.messageQueue.take();
                boolean loop = true;
                while (loop) {
                    System.out.print("来自【" + userName + "】的好友申请，Y（同意)or N（拒绝）：");
                    String agreed = ChatClient.scanner.nextLine();
                    switch (agreed.toUpperCase()) {
                        case "Y":
                            HttpUtil.printRequest(out, RouteEnum.APPLY_FOR_FRIENDS.getPath(), userName);
                            loop = false;
                            break;
                        case "N":
                            HttpUtil.printRequest(out, RouteEnum.REMOVE_FRIENDS.getPath(), userName);
                            loop = false;
                            break;
                        default:
                            System.out.println("请勿输入其他字符！");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
