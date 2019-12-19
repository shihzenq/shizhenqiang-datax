package com.wugui.dataxweb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "system_log_entity")
public class SystemLogEntity extends BaseEntity{

    @ApiModelProperty(value = "姓名", notes = "姓名")
    private String name;

    private Long userId;

    private String ip;

    private String content;

    private String operation;
}
