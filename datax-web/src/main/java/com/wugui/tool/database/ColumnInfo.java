package com.wugui.tool.database;

import lombok.Data;

/**
 * 字段信息
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/7/30
 */
@Data
public class ColumnInfo {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否是主键列
     */
    private Boolean ifPrimaryKey;

    private Integer columnSize;

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                ", ifPrimaryKey=" + ifPrimaryKey +
                ", columnSize=" + columnSize +
                '}';
    }
}