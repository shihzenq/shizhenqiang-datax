package com.wugui.dataxweb.controller;

import com.wugui.dataxweb.config.security.OAuth2Service;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController extends BaseController{

    private OAuth2Service oAuth2Service;

    @ApiOperation(value = "系统退出", notes = "系统退出")
    @PostMapping("/do")
    public ResponseData logout() {
        OAuth2Authentication oAuth2Authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)oAuth2Authentication.getDetails();
        oAuth2Service.logout(details.getTokenValue());
        return response("退出成功！");
    }

    @Autowired
    public void setOAuth2Service(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }
}
