package com.wugui.dataxweb.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GroupDeleteDTO {

    @NotNull(message = "id不能为空！")
    @ApiModelProperty(value = "组名id")
    private Long id;
}
