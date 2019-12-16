package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import com.wugui.dataxweb.util.KlksRedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private KlksRedisUtils redis;

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
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            HttpSession session = request.getSession();
            String userId = (String)session.getAttribute("userId");
            UserEntity user = redis.getUserInfoFromCache(userId);
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {
                return permissionService.validatePermissionCodeExist(user, requiredPermission.value());
            }
            if(requiredPermission != null && requiredPermission.values().length > 0) {
                boolean hasPermission = false;
                for(String p : requiredPermission.values()) {
                    hasPermission = permissionService.validatePermissionCodeExist(user, p);
                    if(hasPermission) break;
                }
                return hasPermission;
            }
        }
        return true;
    }
}
