package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.config.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthToken", description = "登录认证token")
public class AuthToken {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "token")
    private Long expireIn;

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
        expireIn = Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }
}