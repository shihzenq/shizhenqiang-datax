package com.wugui.dataxweb.controller;


import com.wugui.dataxweb.config.Constants;
import com.wugui.dataxweb.dto.user.LoginDTO;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.interceptor.ExcludeInterceptor;
import com.wugui.dataxweb.log.OperateLog;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.JwtUtil;
import com.wugui.dataxweb.util.KlksRedisUtils;
import com.wugui.dataxweb.vo.ResponseData;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/login")
@Api(tags = "登录接口")
public class LoginController extends BaseController{

    private UserService userService;

    @Resource
    KlksRedisUtils klksRedisUtils;

    @ExcludeInterceptor
    @PostMapping
    @OperateLog(content = "登录")
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
        } catch (Exception e) {
            e.printStackTrace();
            return responseError("用户名或密码错误！", 401);
        }
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
