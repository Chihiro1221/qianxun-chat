package com.haonan.server.utils;

import java.sql.*;

/**
 * JDBC工具类
 *
 * @author wanghaonan
 */
public class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost/Network213Chat";
    private static final String USER = "root";
    private static final String PASSWORD = "youzhi..";

    // 静态代码块加载并注册JDBC驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LoggerUtils.logInfo("database has initialed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 关闭数据库连接
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭连接
    public static void closeStatement(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Connection conn = resultSet.getStatement().getConnection();
                Statement stmt = resultSet.getStatement();
                resultSet.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 执行查询
    public static ResultSet executeResultSetQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            resultSet = pstmt.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 执行更新
    public static boolean executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection(conn);
        }
        return false;
    }
}