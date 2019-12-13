package com.wugui.dataxweb.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "user")
public class UserEntity extends BaseEntity{

    private String name;

    private String username;

    private int sex;

    private String phone;

    private String password;

    private Long ucUid;
}
