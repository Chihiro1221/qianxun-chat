package com.haonan.client;

import com.haonan.client.service.OperationTypeFactory;
import com.haonan.client.service.OperatorType;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 聊天客户端
 *
 * @author wanghaonan
 */
public class ChatClient {
    private static Boolean running = true;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    public static final Scanner scanner = new Scanner(System.in);
    private static final ExecutorService pool = Executors.newFixedThreadPool(100);
    public static final LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        try {
            String host = "127.0.0.1";
            Integer port = 8080;
            if (args.length > 0) {
                host = args[0];
            }
            socket = new Socket(host, port);
            System.out.println("\033[1;32m已连接到聊天服务器【" + host + ":" + port + "】，请先注册账号！\033[0m");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            // 开启监听器接收服务端消息
            pool.execute(new ListenerThread(in, out));
            receiveUserOperator();
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("Socket连接失败！请稍后重试");
        }
    }

    /**
     * 接受用户输入
     */
    public static void receiveUserOperator() {
        while (running) {
            try {
                // 输出界面
                printInterface();
                String input = scanner.nextLine();
                int opNum = Integer.parseInt(input);
                OperatorType handler = OperationTypeFactory.getHandler(opNum);
                if (handler == null) {
                    System.out.println("输出参数错误！");
                } else {
                    handler.handle(in, out);
                }
            } catch (Exception e) {
                System.out.println("\033[1;31m请勿输入其他字符！\033[0m");
                //running = false;
                //e.printStackTrace();
            }
        }
    }

    /**
     * 打印菜单项
     */
    private static void printInterface() {
        // 输出系统菜单
        synchronized (System.out) {
            System.out.println("\033[1;34m******* 网络213在线聊天系统 *******\033[0m");
            System.out.println("  \033[1;34m**** Design By Mr.Wang ****\033[0m");
            System.out.println("\033[1;32m0. 注册登录\033[0m");
            System.out.println("\033[1;32m1. 查询系统在线用户\033[0m");
            System.out.println("\033[1;32m2. 添加好友\033[0m");
            System.out.println("\033[1;32m3. 开始聊天\033[0m");
            System.out.println("\033[1;32m4. 处理好友申请\033[0m");
            System.out.println("\033[1;32m5. 退出系统\033[0m");
            System.out.println("\033[1;36m请输入选项(0-5):\033[0m ");
        }
    }


    public static Boolean getRunning() {
        return running;
    }

    public static void setRunning(Boolean running) {
        ChatClient.running = running;
    }

    /**
     * 关闭所有资源
     */
    public static void closeResource() {
        try {
            running = false;
            socket.close();
            in.close();
            out.close();
            pool.shutdown();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
