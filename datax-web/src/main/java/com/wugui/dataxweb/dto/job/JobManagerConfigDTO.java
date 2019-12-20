package com.wugui.dataxweb.dto.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JobManagerConfigDTO {

    @ApiModelProperty(value = "作业名称", notes = "作业名称")
    @NotEmpty(message = "作业名称不能为空！")
    private String jobName;

    @ApiModelProperty(value = "组名称", notes = "组名称")
    @NotEmpty(message = "组名称不能为空！")
    private String groupName;

    @ApiModelProperty(value = "json配置", notes = "json配置")
    @NotEmpty(message = "json配置不能为空！")
    private String jobJson;
}
