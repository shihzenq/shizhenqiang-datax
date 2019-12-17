package com.wugui.dataxweb.util;

import org.openxmlformats.schemas.drawingml.x2006.chart.STLayoutMode;

import java.sql.*;
import java.util.List;

public class JDBCUtils {

    static {
        try {
            Class.forName(DriverUtils.driverMap.get("MYSQL"));//加载数据库驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JDBCUtils() {
    }

    public static Connection getConn(String connection, String username, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connection, username, password);//连接数据库
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStmt(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static PreparedStatement getPStmt(Connection conn, String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    public static void closeStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet executeQuery(Statement stmt, String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void closeRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(Connection conn, String sql) {//重载
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static String createTable(String tableName, List<String> colunmList) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"CREATE TABLE `\"+tableName+\"` (");
        int len = colunmList.size();
        for (int i = 0; i < len; i++) {
            if (i == len-1) {
                sb.append("`"+colunmList.get(i)+"` varchar(100) DEFAULT NULL,)");
            } else {
                sb.append("`"+colunmList.get(i)+"` varchar(100) DEFAULT NULL");
            }
        }
        sb.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return sb.toString();
    }

    public static String renameTableName(String oldName, String targetName) {
        return "rename table "+oldName+" to "+targetName+"";
    }
}
