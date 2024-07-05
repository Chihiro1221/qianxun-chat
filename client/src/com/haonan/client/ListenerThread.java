package com.haonan.client;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 监听服务端消息线程
 *
 * @author wanghaonan
 */
public class ListenerThread implements Runnable {
    private BufferedReader in = null;
    private PrintWriter out = null;

    public ListenerThread(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        boolean running = true;
        try {
            while (running) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = in.readLine()) != null && !line.isEmpty() && !line.equals("\r\n")) {
                    if (line.equals("连接关闭")) {
                        running = false;
                        break;
                    }
                    response.append(line + "\r\n");
                }
                if (!response.toString().isEmpty()) {
                    // 好友申请
                    String data = response.toString();
                    if (data.startsWith("好友申请")) {
                        // 将该消息存储到消息队列中
                        String userName = data.substring(data.indexOf("【") + 1, data.lastIndexOf("】"));
                        ChatClient.messageQueue.put(userName);
                        System.out.print("\033[1;34m[服务端消息]：\033[0m" + response);
                    } else if (data.contains("来自好友")) {
                        System.out.print(response);
                    } else {
                        System.out.print("\033[1;34m[服务端消息]：\033[0m" + response);
                    }
                    if (data.contains("顶下线")) {
                        ChatClient.closeResource();
                        running = false;
                    }
                }
                //ChatClient.receiveUserOperator();
            }
        } catch (Exception e) {
            //running = false;
            e.printStackTrace();
        } finally {
            ChatClient.closeResource();
        }
    }
}