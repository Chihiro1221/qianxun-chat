package com.haonan.server.utils;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 响应工具类
 */
public class HttpUtil {
    public static void printRequest(PrintWriter out, String path) {
        String hostAddress = "127.0.0.1";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("未知主机错误！");
            //throw new RuntimeException(e);
        }
        out.println(String.format("POST /api/%s HTTP/1.1", path));
        out.println("Content-Type: application/json");
        out.println("Host: " + hostAddress);
        out.println("Connection: keep-alive");
        out.println();
        out.println();
        out.flush();
    }

    public static void printRequest(PrintWriter out, String path, String data) {
        out.println(String.format("POST /api/%s HTTP/1.1", path));
        out.println("Content-Type: application/json");
        out.println("Host: 127.0.0.1:8080");
        out.println("Connection: keep-alive");
        out.println();
        out.println(data);
        out.println();
        out.flush();
    }

    public static void printRequest(PrintWriter out, String path, String connection, String data) {
        out.println(String.format("POST /api/%s HTTP/1.1", path));
        out.println("Content-Type: application/json");
        out.println("Host: 127.0.0.1:8080");
        out.println(String.format("Connection: %s", connection));
        out.println();
        out.println(data);
        out.println();
        out.flush();
    }

    public static void printResponse(PrintWriter out, Integer statusNum, String data) {
        // 输出状态行
        //out.println("HTTP/1.1 " + statusNum + " " + "ok");
        //
        //// 输出响应头
        //out.println("Content-Type: application/json; charset=UTF-8");
        //out.println("Content-Length: " + data.length());
        //out.println("Connection: keep-alive");
        //out.println("Server: QxChatServer/1.0");
        //out.println();

        // 输出响应体
        out.println(data);
        out.println();
        out.flush();
    }
}
