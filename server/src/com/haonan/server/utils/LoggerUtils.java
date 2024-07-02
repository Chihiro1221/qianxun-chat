package com.haonan.server.utils;

import java.util.logging.Logger;

/**
 * 日志工具
 */
public class LoggerUtils {
    private static final Logger logger = Logger.getLogger(LoggerUtils.class.getName());

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }

    public static void logError(String message) {
        logger.severe(message);
    }
}
