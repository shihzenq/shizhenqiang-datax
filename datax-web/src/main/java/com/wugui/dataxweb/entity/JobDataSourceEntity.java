package com.wugui.dataxweb.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "job_data_source")
public class JobDataSourceEntity extends BaseEntity {

    /**
     * 连接名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * jdbc url
     */
    private String jdbcUrl;

    /**
     * jdbc驱动类
     */
    private String jdbcDriverClass;
}
