package com.wugui.dataxweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "role")
public class Role extends BaseEntity {
    private Long id;

    private String name;

    private Short type;

    private String description;

    @TableField(exist = false)
    private List<Permission> permissionList = new ArrayList<>();
}