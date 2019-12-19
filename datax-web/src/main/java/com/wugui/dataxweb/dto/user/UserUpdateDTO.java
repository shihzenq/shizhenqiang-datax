package com.wugui.dataxweb.dto.user;

import com.wugui.dataxweb.validator.annotation.Patterns;
import com.wugui.dataxweb.validator.group.OnUpdateCheap;
import com.wugui.dataxweb.validator.group.UpdateChecks;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "用户修改接受类")
public class UserUpdateDTO implements Serializable {

    @ApiModelProperty(value = "用户id", notes = "用户id")
    @NotNull(message = "用户id不能为空！", groups = OnUpdateCheap.class)
    private Long id;

    @ApiModelProperty(value = "用户名称", notes = "用户名称")
    @NotEmpty(message = "用户名不能为空！")
    private String username;

    @ApiModelProperty(value = "性别", notes = "用户性别")
    private String sex;

    @ApiModelProperty(value = "用户名称", notes = "用户名称")
    @NotEmpty(message = "手机号不能为空！")
    @Patterns(pattern = "phone", message = "手机号格式不正确！")
    private String phone;

    @ApiModelProperty(value = "备注", notes = "备注")
    private String remark;

}
