package com.wugui.dataxweb.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "role_permission")
public class RolePermission {

    private Long roleId;

    private Long permissionId;
}
