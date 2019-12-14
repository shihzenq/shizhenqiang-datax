package com.wugui.dataxweb.controller;


import com.wugui.dataxweb.config.security.AccessToken;
import com.wugui.dataxweb.config.security.AuthInfo;
import com.wugui.dataxweb.config.security.AuthToken;
import com.wugui.dataxweb.config.security.OAuth2Service;
import com.wugui.dataxweb.dto.user.LoginDTO;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wugui.dataxweb.config.Constants.CAPTCHA_ID_HEADER;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{

    private AuthInfo authInfo;

    private OAuth2Service oAuth2Service;

    private UserService userService;

    @ApiOperation(value = "登录接口", notes = "登录接口，请求头中必须携带 "+CAPTCHA_ID_HEADER+" 参数，该参数从[图形验证码接口]的响应头中获取")
    @PostMapping
    public ResponseData login(@Validated @RequestBody LoginDTO loginForm, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws IOException {

        if(bindingResult.hasErrors()) {
            return responseFormError(bindingResult);
        }
        try {
            AccessToken accessToken = oAuth2Service.getAccessToken(loginForm.getUsername(), loginForm.getPassword(), request.getRemoteAddr());
            String requestNo = accessToken.getJti();
            UserEntity enterpriseUser = userService.getByUsername(accessToken.getUsername());
            if (null == enterpriseUser) {
                // erp存在用户，但悟空小子不存在，无法登陆
                return responseError("用户名或密码错误！", 401);
            }
            authInfo.setUserId(requestNo, enterpriseUser.getId());
            return response(new AuthToken(accessToken.getAccessToken()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return responseError("用户名或密码错误！", 401);
        }
    }

    @Autowired
    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Autowired
    public void setOAuth2Service(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
