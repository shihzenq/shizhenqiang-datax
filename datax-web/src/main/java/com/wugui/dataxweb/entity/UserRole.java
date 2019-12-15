package com.wugui.dataxweb.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "user_role")
public class UserRole {

    private Long userId;

    private Long roleId;
}
