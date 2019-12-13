package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class LockInterceptor implements HandlerInterceptor {

    private IAuthenticationFacade authenticationFacade;

    private RedisTemplate<Object, Object> template;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(lock(request, response, handler)) {
            return true;
        }
        response.sendError(421, "重复提交");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        template.delete(getLockKey(request));
    }

    @Autowired
    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    private boolean lock(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 首先获取控制器方法上的锁注解
            Lock lock = handlerMethod.getMethod().getAnnotation(Lock.class);
            // 如果方法上没有锁注解，则获取类上的锁注解
            if (lock == null) {
                lock = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Lock.class);
            }
            // 如果标记了注解，则尝试获取锁
            if (lock != null) {
                return tryGetLock(getLockKey(request), lock);
            }
        }
        return true;
    }

    private boolean tryGetLock(String key, Lock lock){
        ValueOperations<Object, Object> objectObjectValueOperations = template.opsForValue();
        while(true) {
            Boolean locked = objectObjectValueOperations.setIfAbsent(key, new Date());
            if(null != locked && locked) {
                return true;
            } else {
                Date date = (Date) objectObjectValueOperations.get(key);
                if(null == date || System.currentTimeMillis() - date.getTime() > lock.expireTime()) {
                    template.delete(key);
                } else {
                    break;
                }
            }
        }
        return false;
    }

    private String getLockKey(HttpServletRequest request) {
        UserEntity user = authenticationFacade.getUser();
        if(null == user) {
            return request.getRequestURL().toString();
        }
        return String.valueOf(user.getId()) + request.getRequestURL();
    }

    @Autowired
    public void setTemplate(RedisTemplate<Object, Object> template) {
        this.template = template;
    }
}
