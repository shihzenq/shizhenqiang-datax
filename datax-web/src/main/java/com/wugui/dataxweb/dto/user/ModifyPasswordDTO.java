package com.wugui.dataxweb.dto.user;

import com.wugui.dataxweb.validator.EqualsField;
import com.wugui.dataxweb.validator.annotation.CurrentUserPassword;
import com.wugui.dataxweb.validator.group.Cheap;
import com.wugui.dataxweb.validator.group.Expensive;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "修改密码数据")
@EqualsField(source = "newPassword", target = "reNewPassword", message = "新密码与确认密码不一致！", groups = Expensive.class)
public class ModifyPasswordDTO{

    @ApiModelProperty(value = "用户当前密码", notes = "用户当前密码", required = true)
    @NotEmpty(message = "原密码不能为空！")
    @CurrentUserPassword(message = "原密码校验不正确，请核对后输入！", groups = Cheap.class)
    private String currentPassword;

    @ApiModelProperty(value = "新密码", notes = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空！")
    @Size(min = 6, max = 20, message = "新密码输入有误，请按密码规则输入！")
    private String newPassword;

    @ApiModelProperty(value = "确认新密码", notes = "确认新密码", required = true)
    @NotEmpty(message = "确认密码不能为空！")
    private String reNewPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }
}
