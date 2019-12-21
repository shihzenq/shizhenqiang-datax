package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.controller.BaseController;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.JSON;
import com.wugui.dataxweb.util.JwtUtil;
import com.wugui.dataxweb.util.KlksRedisUtils;
import com.wugui.dataxweb.vo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PermissionInterceptor extends BaseController implements HandlerInterceptor {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private KlksRedisUtils redis;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(checkPermission(request, response, handler)) {
            return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "没有权限");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    private boolean checkPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 首先获取控制器方法上的权限注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上没有权限注解，则获取类上的权限注解
            if (requiredPermission == null) {
//                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
                return true;
            }
            try {
                String token = request.getHeader("Authorization");
                if (StringUtils.isNotBlank(token)) {
                    String userId = JwtUtil.getUsername(token);
                    if (StringUtils.isNotBlank(userId)) {
                        UserEntity entity = userService.getById(Long.parseLong(userId));
                        if (null != entity) {
                            if (permissionService.validatePermissionPathExist(entity, requiredPermission.value())){
                                return true;
                            } else {
                                ResponseData<T> responseData = new ResponseData<>();
                                responseData.setCode(400);
                                responseData.setMessage("您当前无此权限，无法操作此功能! ");
                                response.setHeader("Content-type", "application/json;charset=UTF-8");
                                response.setCharacterEncoding("UTF-8");
                                response.getWriter().write(JSON.marshal(responseData));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
