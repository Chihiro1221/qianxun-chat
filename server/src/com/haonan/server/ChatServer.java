package com.haonan.server;

import com.haonan.server.service.RouteMappingFactory;
import com.haonan.server.utils.LoggerUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 聊天服务端
 */
public class ChatServer extends HandleChatThread {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(1000);
    private static ServerSocket serverSocket = null;

    public ChatServer(Socket connection) {
        super(connection);
    }

    /**
     * 在线用户列表
     */
    public static final Map<String, PrintWriter> ONLINE_USERS = new ConcurrentHashMap<String, PrintWriter>();


    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            serverSocket = new ServerSocket(8080);
            LoggerUtils.logInfo("Server Running at http://127.0.0.1:8080");
            while (true) {
                Socket connection = serverSocket.accept();
                POOL.execute(new HandleChatThread(connection));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("启动ServerSocket服务失败");
        } finally {
            try {
                POOL.shutdown();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
