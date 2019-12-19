package com.wugui.dataxweb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class LogOffDTO implements Serializable{


    @NotNull(message = "用户id不能为空！")
    private Long userId;


    @ApiModelProperty("用户名")
    private String username;
}
