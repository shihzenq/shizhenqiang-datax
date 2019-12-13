package com.wugui.dataxweb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Permission implements Serializable {
    private Long id;

    private String code;

    private String name;

    private String path;

    private Boolean isLeaf;

    private Long parentId;

    private Short level;

    private Integer sort;

    private List<Permission> children = new ArrayList<Permission>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Permission> getChildren() {
        return children == null ? new ArrayList<Permission>() : children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }
}