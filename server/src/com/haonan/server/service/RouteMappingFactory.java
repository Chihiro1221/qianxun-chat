package com.haonan.server.service;

import com.haonan.server.controller.*;
import com.haonan.server.enums.RouteEnum;

import java.util.HashMap;

/**
 * 操作工厂类
 *
 * @author wanghaoann
 */
public class RouteMappingFactory {
    private static HashMap<RouteEnum, RouteMapping> routeMappingTable = new HashMap<>();

    static {
        RouteMapping showUsersHandler = new ShowUsersController();
        RouteMapping registerLoginHandler = new RegisterLoginController();
        RouteMapping applyForUserHandler = new ApplyForUserController();
        RouteMapping beginCommunicateHandler = new BeginCommunicateController();
        RouteMapping exitHandler = new ExitController();
        RouteMapping removeFriendController = new RemoveFriendController();
        RouteMapping showAllFriendController = new ShowAllFriendController();

        routeMappingTable.put(showUsersHandler.getRouteEnum(), showUsersHandler);
        routeMappingTable.put(registerLoginHandler.getRouteEnum(), registerLoginHandler);
        routeMappingTable.put(applyForUserHandler.getRouteEnum(), applyForUserHandler);
        routeMappingTable.put(beginCommunicateHandler.getRouteEnum(), beginCommunicateHandler);
        routeMappingTable.put(exitHandler.getRouteEnum(), exitHandler);
        routeMappingTable.put(removeFriendController.getRouteEnum(), removeFriendController);
        routeMappingTable.put(showAllFriendController.getRouteEnum(), showAllFriendController);
    }

    /**
     * 根据枚举值获取handler
     *
     * @param path
     * @return
     */
    public RouteMapping getHandler(String path) {
        RouteEnum routeEnum = RouteEnum.getRouteEnum(path);
        RouteMapping routeMapping = routeMappingTable.get(routeEnum);
        return routeMapping;
    }
}
