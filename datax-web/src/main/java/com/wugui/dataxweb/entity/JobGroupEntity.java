package com.wugui.dataxweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "job_group_entity")
public class JobGroupEntity extends BaseEntity{

    private Long id;

    private String name;

    private String remark;

    @TableField(exist = false)
    private String userName;
}
