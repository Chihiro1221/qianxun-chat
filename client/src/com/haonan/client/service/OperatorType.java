package com.haonan.client.service;

import com.haonan.client.enums.OperatorEnum;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 策略接口
 *
 * @author wanghaonan
 */
public interface OperatorType {
    /**
     * 获取当前策略实现类的枚举类型
     * @return
     */
    OperatorEnum getOperatorTypeEnum();

    /**
     * 通用处理类
     * @param in
     * @param out
     */
    void handle(BufferedReader in, PrintWriter out);
}
