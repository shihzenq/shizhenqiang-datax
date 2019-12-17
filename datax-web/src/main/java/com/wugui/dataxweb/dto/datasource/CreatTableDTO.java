package com.wugui.dataxweb.dto.datasource;

import com.wugui.dataxweb.validator.group.Cheap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("创建表")
public class CreatTableDTO implements Serializable {

    @ApiModelProperty(value = "表名", required = true)
    @NotEmpty(message = "请输入表名！")
    private String tableName;

    @ApiModelProperty(value = "数据源id", required = true)
    @NotNull(message = "请输入数据源id ！")
    private Long id;

    @NotNull(message = "添加字段不能为空!", groups = Cheap.class)
    @Size(min = 1, message = "添加字段至少为一条!")
    @ApiModelProperty(value = "字段", required = true)
    private List<String> colunmList = new ArrayList<>();
}
