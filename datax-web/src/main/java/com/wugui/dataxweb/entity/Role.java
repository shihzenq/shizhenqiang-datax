package com.wugui.dataxweb.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role extends BaseEntity {
    private Long id;

    private String name;

    private Long userId;

    private Short type;

    private String description;

    private List<Permission> permissionList = new ArrayList<>();
}