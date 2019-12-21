package com.wugui.dataxweb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "字段名展示类")
public class DataSourceColumnVO implements Serializable {

    @ApiModelProperty(value = "数据源id")
    private Long id;

    @ApiModelProperty(value = "字段名")
    private List<String> columnList = new ArrayList<>();
}
