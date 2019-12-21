package com.wugui.dataxweb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "表名展示类")
public class DataSourceTablesVO implements Serializable {

    @ApiModelProperty(value = "数据源id")
    private Long id;

    @ApiModelProperty(value = "表名")
    private List<String> tableList = new ArrayList<>();
}
