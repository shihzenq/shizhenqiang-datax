package com.wugui.dataxweb.util;

import com.wugui.tool.database.ColumnInfo;
import com.wugui.tool.database.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    private static Map<String, String> columsType = new HashMap<>();

    static {
        try {
            Class.forName(DriverUtils.driverMap.get("MYSQL"));//加载数据库驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        columsType.put("java.math.BigInteger", "bigint");
        columsType.put("java.lang.Integer", "int");
        columsType.put("java.sql.Date", "date");
        columsType.put("java.lang.String", "varchar");
        columsType.put("java.lang.Boolean", "tinyint");
        columsType.put("java.math.BigDecimal", "decimal");
        columsType.put("java.sql.Timestamp", "DATETIME");
        columsType.put("java.lang.byte[]", "BLOB");
        columsType.put("java.lang.Float", "FLOAT");
        columsType.put("java.lang.Long", "int");
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
        sb.append("CREATE TABLE `"+tableName+"` (");
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

    public static String createTableSync(TableInfo sourceTableInfo, String targetTableName){
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE `"+targetTableName+"` (");
        List<ColumnInfo> columnInfoList = sourceTableInfo.getColumns();
        int len = columnInfoList.size();
        String primaryKey = "";
        for (ColumnInfo t : columnInfoList) {
            if (t.getIfPrimaryKey()) {
                primaryKey = t.getName();
                break;
            }
        }
        for (int i = 0; i < len; i++) {
            ColumnInfo columnInfo = columnInfoList.get(i);
            if (columnInfo == null) continue;
            // NOT NULL
            boolean equals = "java.sql.Date".equals(columnInfo.getType()) || "java.sql.Timestamp".equals(columnInfo.getType());
            if (i == len-1) {
                if (StringUtils.isNotBlank(columnInfo.getComment())) {
                    sb.append("`"+columnInfo.getName()+"` "+ columsType.get(columnInfo.getType())+(equals? " " : "("+columnInfo.getColumnSize()+") ")+(StringUtils.isNotBlank(primaryKey) && primaryKey.equals(columnInfo.getName())? "NOT NULL" : "DEFAULT NULL ")+"COMMENT '"+columnInfo.getComment()+"'");
                    sb.append(StringUtils.isNotBlank(primaryKey) ? "," : ")");
                } else {
                    sb.append("`"+columnInfo.getName()+"` "+ columsType.get(columnInfo.getType())+(equals? " " : "("+columnInfo.getColumnSize()+") ")+(StringUtils.isNotBlank(primaryKey) && primaryKey.equals(columnInfo.getName())? "NOT NULL" : "DEFAULT NULL")+"");
                    sb.append(StringUtils.isNotBlank(primaryKey) ? "," : ")");
                }
            } else {
                if (StringUtils.isNotBlank(columnInfo.getComment())) {
                    sb.append("`"+columnInfo.getName()+"` "+ columsType.get(columnInfo.getType())+(equals? " " : "("+columnInfo.getColumnSize()+") ")+(StringUtils.isNotBlank(primaryKey) && primaryKey.equals(columnInfo.getName())? "NOT NULL" : "DEFAULT NULL ")+" COMMENT '"+columnInfo.getComment()+"',");
                } else {
                    sb.append("`"+columnInfo.getName()+"` "+ columsType.get(columnInfo.getType())+(equals? " " : "("+columnInfo.getColumnSize()+") ")+(StringUtils.isNotBlank(primaryKey) && primaryKey.equals(columnInfo.getName())? "NOT NULL" : "DEFAULT NULL")+",");
                }
            }
        }
        if (StringUtils.isNotBlank(primaryKey)) {
            sb.append("PRIMARY KEY (`"+primaryKey+"`) USING BTREE)");
        }
        sb.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return sb.toString();
    }


}
