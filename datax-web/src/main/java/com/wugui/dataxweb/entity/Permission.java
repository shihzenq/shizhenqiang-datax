package com.wugui.dataxweb.entity;

import lombok.Data;

@Data
public class Permission extends BaseEntity {
    private Long id;

    private String code;

    private String name;

    private String path;

    private Boolean isLeaf;

    private Long parentId;

    private Short level;

    private Integer sort;
}