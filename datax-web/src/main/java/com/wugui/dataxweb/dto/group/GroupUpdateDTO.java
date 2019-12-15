package com.wugui.dataxweb.dto.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "组名修改接受类")
public class GroupUpdateDTO {

    @NotNull(message = "id不能为空！")
    @ApiModelProperty(value = "组名id")
    private Long id;

    @ApiModelProperty(value = "组名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;
}
