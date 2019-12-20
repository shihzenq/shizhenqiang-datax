package com.wugui.dataxweb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "job_manager_entity")
@ApiModel(value = "作业配置实体类")
public class JobManagerEntity extends BaseEntity {

    @ApiModelProperty(value = "作业名")
    private String jobName;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "组id")
    private Long groupId;

    @ApiModelProperty(value = "输入端ip")
    private String sourceIp;

    @ApiModelProperty(value = "输出端ip")
    private String targetIp;

    @ApiModelProperty(value = "状态, 0:未开启，1：进行中")
    private Integer status = 0;

    @ApiModelProperty(value = "创建人名字")
    private String createUserName;

    @ApiModelProperty(value = "执行的json字符串")
    private String jobJson;

    @ApiModelProperty(value = "备注")
    private String remark;
}
