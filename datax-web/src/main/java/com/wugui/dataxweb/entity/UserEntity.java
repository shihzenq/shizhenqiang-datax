package com.wugui.dataxweb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "user_entity")
public class UserEntity extends BaseEntity{

    @ApiModelProperty(value = "姓名", notes = "姓名")
    private String name;

    @ApiModelProperty(value = "用户名称", notes = "用户名称")
    private String username;

    /**
     * 0：女
     * 1：男
     */
    private String sex;

    private String phone;

    private String password;

    private Boolean admin;

    private String token;

    private String remark;
}
