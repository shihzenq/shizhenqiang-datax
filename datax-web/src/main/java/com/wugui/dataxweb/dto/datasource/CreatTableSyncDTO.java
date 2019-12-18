package com.wugui.dataxweb.dto.datasource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("创建表, 将源数据库表及字段信息同步创建目标源数据库表中")
public class CreatTableSyncDTO implements Serializable {

    @ApiModelProperty(value = "源表名", required = true)
    @NotEmpty(message = "请输入源表名！")
    private String sourceTableName;

    @ApiModelProperty(value = "源数据源id", required = true)
    @NotNull(message = "请输入源数据源id ！")
    private Long sourceId;

    @ApiModelProperty(value = "目标表名", required = true)
    @NotEmpty(message = "请输入目标表名！")
    private String targetTableName;

    @ApiModelProperty(value = "目标数据源id", required = true)
    @NotNull(message = "请输入目标数据源id ！")
    private Long targetId;
}
