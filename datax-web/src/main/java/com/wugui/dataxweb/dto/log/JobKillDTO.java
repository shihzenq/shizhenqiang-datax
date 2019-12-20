package com.wugui.dataxweb.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JobKillDTO {

    @ApiModelProperty(value = "操作进程id")
    @NotEmpty(message = "操作系统进程id不能为空！")
    private String pid;

    @ApiModelProperty(value = "作业数据id")
    @NotNull(message = "作业数据id不能为空！")
    private Long jobManagerId;
}
