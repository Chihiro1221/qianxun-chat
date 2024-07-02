package com.haonan.client.enums;

/**
 * 消息类型枚举类
 *
 * @author wanghaonan
 */
public enum MessageEnum {
    APPLY_FOR_FRIEND("apply_for_friend", "申请好友");
    private String type;
    private String msg;

    MessageEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static MessageEnum getMessageTypeEnum(String type) {
        for (MessageEnum messageEnum : MessageEnum.values()) {
            if (messageEnum.type.equals(type)) {
                return messageEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
