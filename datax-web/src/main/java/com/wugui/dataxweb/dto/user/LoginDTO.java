package com.wugui.dataxweb.dto.user;

import com.wugui.dataxweb.validator.annotation.Captcha;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel(value = "LoginForm", description = "用户登录")
public class LoginDTO implements Serializable {

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "请输入用户名/手机号！")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "请输入密码！")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
