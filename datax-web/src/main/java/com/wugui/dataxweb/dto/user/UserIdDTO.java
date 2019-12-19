package com.wugui.dataxweb.dto.user;

import com.wugui.dataxweb.validator.group.OnUpdateCheap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "用户id参数接受类")
public class UserIdDTO {

    @ApiModelProperty(value = "用户id", notes = "用户id")
    @NotNull(message = "用户id不能为空！", groups = OnUpdateCheap.class)
    private Long id;
}
