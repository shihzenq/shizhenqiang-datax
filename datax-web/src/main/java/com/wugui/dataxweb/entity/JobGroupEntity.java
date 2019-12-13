package com.wugui.dataxweb.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "job_group")
public class JobGroupEntity extends BaseEntity{

    private Long id;

    private String name;

    private String remark;
}
