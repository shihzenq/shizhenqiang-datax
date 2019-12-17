package com.wugui.dataxweb.util;

import java.util.HashMap;
import java.util.Map;

public class DriverUtils {
    public static Map<String, String> driverMap = new HashMap<>();
    static {
        driverMap.put("HSQLDB", "org.hsqldb.jdbcDriver");
        driverMap.put("MYSQL", "com.mysql.jdbc.Driver");
        driverMap.put("POSTGRESQL", "org.postgresql.Driver");
        driverMap.put("ORACLE", "oracle.jdbc.driver.OracleDriver");
        driverMap.put("SQLServer", "net.sourceforge.jtds.jdbc.Driver");
    }
}
