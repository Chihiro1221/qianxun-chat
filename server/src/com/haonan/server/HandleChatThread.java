package com.haonan.server;

import com.haonan.server.entity.User;
import com.haonan.server.service.RouteMapping;
import com.haonan.server.service.RouteMappingFactory;
import com.haonan.server.utils.HttpUtil;
import com.haonan.server.utils.LoggerUtils;
import com.haonan.server.utils.UserHolder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理聊天线程类
 *
 * @author wanghaonan
 */
public class HandleChatThread implements Runnable {
    private Socket connection;
    private Boolean isClose = false;

    public HandleChatThread(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
        ) {
            while (!isClose) {
                // 读取请求行
                String requestLine = in.readLine();
                // 获取请求头
                Map<String, String> headers = new HashMap<>();
                String header;
                while (!(header = in.readLine()).isEmpty()) {
                    int index = header.indexOf(":");
                    String key = header.substring(0, index).trim();
                    String value = header.substring(index + 1).trim();
                    headers.put(key, value);
                }
                // 读取请求体
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null && line.length() > 0) {
                    requestBody.append(line).append("\n");
                }
                // 分割请求路径，完成路由功能
                String[] split = requestLine.split(" ");
                // 处理请求
                String path = split[1].substring(split[1].indexOf("/api/") + 5);
                // 记录请求日志
                StringBuilder requestLog = new StringBuilder();
                requestLog.append("收到客户端请求:\r\n");
                requestLog.append("请求行: " + requestLine + "\r\n");
                requestLog.append("请求头: " + headers + "\r\n");
                requestLog.append("请求体: " + requestBody);
                requestLog.append("请求资源: " + path + "\r\n");
                LoggerUtils.logInfo(requestLog.toString());
                RouteMappingFactory routeMappingFactory = new RouteMappingFactory();
                RouteMapping handler = routeMappingFactory.getHandler(path);
                if (handler == null) {
                    HttpUtil.printResponse(out, 404, "请求资源不存在！");
                    return;
                }
                handler.handle(requestBody.toString(), out);
            }
        } catch (SocketException e) {
            User user = UserHolder.getUser();
            ChatServer.ONLINE_USERS.remove(user.getUsername());
            ChatServer.ONLINE_USERS.forEach((userName, userOut) -> {
                HttpUtil.printResponse(userOut, 200, "用户【" + user.getUsername() + "】已下线！");
            });
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.logError("获取流失败！");
        }
    }
}
