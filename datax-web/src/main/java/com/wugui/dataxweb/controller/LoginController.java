package com.wugui.dataxweb.controller;


import com.wugui.dataxweb.config.Constants;
import com.wugui.dataxweb.dto.user.LoginDTO;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.JwtUtil;
import com.wugui.dataxweb.util.KlksRedisUtils;
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

import javax.annotation.Resource;

import static com.wugui.dataxweb.config.Constants.CAPTCHA_ID_HEADER;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{

    private UserService userService;

    @Resource
    KlksRedisUtils klksRedisUtils;

    @ApiOperation(value = "登录接口", notes = "登录接口，请求头中必须携带 "+CAPTCHA_ID_HEADER+" 参数，该参数从[图形验证码接口]的响应头中获取")
    @PostMapping
    public ResponseData<?> login(@Validated @RequestBody LoginDTO loginForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return responseFormError(bindingResult);
        }
        try {
            UserEntity enterpriseUser = userService.getByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword());
            if (null == enterpriseUser) {
                // erp存在用户，但悟空小子不存在，无法登陆
                return responseError("用户名或密码错误！", 401);
            }
            Long userId = enterpriseUser.getId();
            //删除以前token
            klksRedisUtils.deleteToken(enterpriseUser.getId().toString());

            String token = JwtUtil.sign(userId.toString(), Constants.TOKEN_SALT);
            klksRedisUtils.saveToken(userId.toString(), token);

            enterpriseUser.setToken(token);
            // 缓存
            klksRedisUtils.saveUserInfoToCache(userId.toString(), enterpriseUser);
            return response(enterpriseUser);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return responseError("用户名或密码错误！", 401);
        }
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
