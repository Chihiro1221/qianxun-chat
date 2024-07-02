package com.haonan.server.service;

import com.haonan.server.enums.RouteEnum;

import java.io.PrintWriter;

/**
 * 路径映射策略接口
 *
 * @author wanghaonan
 */
public interface RouteMapping {
    /**
     * 获取当前策略实现类的枚举类型
     * @return
     */
    RouteEnum getRouteEnum();

    /**
     * 通用处理类
     */
    void handle(String requestBody, PrintWriter out);
}
