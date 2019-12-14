package com.wugui.dataxweb.controller;


import com.wugui.dataxweb.config.security.IAuthenticationFacade;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.vo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    HttpServletRequest request;

    private IAuthenticationFacade authenticationFacade;

    StringRedisTemplate template;

    <T> ResponseData<T> response(T data, String message, Integer code){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode(code);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    ResponseData<String> response() {
        return response("OK", "OK", 200);
    }

    <T> ResponseData<T> response(T data) {
        return response(data, "OK", 200);
    }

    <T> ResponseData<T> responseError(String message) {
        return responseError(message, 400);
    }

    <T> ResponseData<T> responseError(String message, Integer code) {
        return response(null, message, code);
    }

    ResponseData<List<ObjectError>> responseFormError(BindingResult bindingResult) {
        String defaultMessage = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
        return response(bindingResult.getAllErrors(), defaultMessage, 400);
    }

    UserEntity getCurrentUser() {
        return authenticationFacade.getUser();
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Autowired
    public void setTemplate(StringRedisTemplate template){
        this.template = template;
    }
}
