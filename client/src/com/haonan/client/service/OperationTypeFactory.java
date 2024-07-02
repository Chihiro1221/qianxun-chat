package com.haonan.client.service;

import com.haonan.client.enums.OperatorEnum;
import com.haonan.client.handler.*;

import java.util.HashMap;

/**
 * 操作工厂类
 *
 * @author wanghaoann
 */
public class OperationTypeFactory {
    private static HashMap<OperatorEnum, OperatorType> hashMap = new HashMap<>();

    static {
        OperatorType showUsersHandler = new ShowUsersHandler();
        OperatorType registerLoginHandler = new RegisterLoginHandler();
        OperatorType applyForUserHandler = new ApplyForUserHandler();
        OperatorType beginCommunicateHandler = new BeginCommunicateHandler();
        OperatorType exitHandler = new ExitHandler();
        OperatorType dealWithApplyForHandler = new DealWithApplyForHandler();
        hashMap.put(showUsersHandler.getOperatorTypeEnum(), showUsersHandler);
        hashMap.put(registerLoginHandler.getOperatorTypeEnum(), registerLoginHandler);
        hashMap.put(applyForUserHandler.getOperatorTypeEnum(), applyForUserHandler);
        hashMap.put(beginCommunicateHandler.getOperatorTypeEnum(), beginCommunicateHandler);
        hashMap.put(exitHandler.getOperatorTypeEnum(), exitHandler);
        hashMap.put(dealWithApplyForHandler.getOperatorTypeEnum(), dealWithApplyForHandler);
    }

    /**
     * 根据枚举值获取handler
     *
     * @param opNum
     * @return
     */
    public OperatorType getHandler(Integer opNum) {
        OperatorEnum operatorEnum = OperatorEnum.getOperatorEnum(opNum);
        OperatorType handler = hashMap.get(operatorEnum);
        return handler;
    }
}
