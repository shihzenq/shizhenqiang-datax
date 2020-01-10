package com.wugui.dataxweb.util;

import java.util.HashMap;
import java.util.Map;

public class DriverUtils {
    public static Map<String, String> driverMap = new HashMap<>();
    static {
        driverMap.put("HSQLDB", "org.hsqldb.jdbcDriver");
        driverMap.put("mysql", "com.mysql.jdbc.Driver");
        driverMap.put("postgresql", "org.postgresql.Driver");
        driverMap.put("oracle", "oracle.jdbc.driver.OracleDriver");
        driverMap.put("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
}
