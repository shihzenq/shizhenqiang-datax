package com.wugui.dataxweb.entity;

import lombok.Data;

@Data
public class Permission extends BaseEntity {
    private Long id;

    private String code;

    private String name;

    private String path;
}