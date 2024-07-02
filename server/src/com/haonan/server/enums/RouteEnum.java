package com.haonan.server.enums;

/**
 * 操作类型枚举类
 *
 * @author wanghaonan
 */
public enum RouteEnum {
    REGISTER_LOGIN("register_login", "注册登录"),
    SHOW_USERS("show_online_users", "展示在线用户信息"),
    APPLY_FOR_FRIENDS("apply_for_friends", "添加好友"),
    SHOW_ALL_FRIENDS("show_all_friends", "显示所有好友"),
    REMOVE_FRIENDS("remove_friend", "删除好友"),
    COMMUNICATION("begin_communication", "开启聊天"),
    EXIT("exit_system", "退出系统");
    private String path;
    private String msg;

    RouteEnum(String path, String msg) {
        this.path = path;
        this.msg = msg;
    }

    public static RouteEnum getRouteEnum(String routePath) {
        for (RouteEnum routeEnum : RouteEnum.values()) {
            if (routeEnum.path.equals(routePath)) {
                return routeEnum;
            }
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
