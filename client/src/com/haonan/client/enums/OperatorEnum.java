package com.haonan.client.enums;

/**
 * 操作类型枚举类
 *
 * @author wanghaonan
 */
public enum OperatorEnum {
    REGISTER_LOGIN(0, "注册登录"),
    SHOW_USERS(1, "展示用户信息"),
    APPLY_FOR_FRIENDS(2, "添加好友"),
    COMMUNICATION(3, "开启聊天"),
    DEAL_WITH_APPLY_FOR(4, "处理好友申请"),
    EXIT(5, "退出系统");
    private Integer opNum;
    private String msg;

    OperatorEnum(Integer opNum, String msg) {
        this.opNum = opNum;
        this.msg = msg;
    }

    public static OperatorEnum getOperatorEnum(Integer opNum) {
        for (OperatorEnum operatorEnum : OperatorEnum.values()) {
            if (operatorEnum.opNum.equals(opNum)) {
                return operatorEnum;
            }
        }
        return null;
    }
}
