package com.wugui.dataxweb.log;

import com.wugui.dataxweb.entity.SystemLogEntity;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.SystemLogService;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.JwtUtil;
import com.wugui.dataxweb.vo.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private final String POINT_CUT = "execution(* com.wugui.dataxweb.controller..*(..))";

    @Autowired
    private UserService userService;

    @Autowired
    private SystemLogService systemLogService;

    @Pointcut(POINT_CUT)
    public void logPointCut() {
    }

    @SuppressWarnings("all")
    @AfterReturning(value = POINT_CUT, returning = "keys")
    public void afterReturning(JoinPoint joinPoint, Object keys) {

        try {
            Object[] args = joinPoint.getArgs();
            String methodName = joinPoint.getSignature().getName();
            ResponseData responseData = null;
            if (keys instanceof ResponseData) {
                responseData = (ResponseData) keys;
            }
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            OperateLog operateLog =
                    method.getAnnotation(OperateLog.class);
            // 打印导出没有返回值也需要记录日志
            if (operateLog != null && (responseData == null || responseData.getCode() == 200)) {
                String content = operateLog.content();
                String path = operateLog.path();
                short type = operateLog.type();
                Short scope = operateLog.scope();
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                if (request == null) return;
                String token = request.getHeader("Authorization");
                UserEntity userEntity = null;
                if (StringUtils.isNotBlank(token)) {
                    String userId = JwtUtil.getUsername(token);
                    if (StringUtils.isNotBlank(userId)) {
                        // uid存入session
                        UserEntity entity = userService.getById(Long.parseLong(userId));
                        if (null != entity) {
                            userEntity = entity;
                        }
                    }
                }
                if (null == userEntity) return;
                SystemLogEntity systemLogEntity = new SystemLogEntity();
                systemLogEntity.setName(userEntity.getName());
                systemLogEntity.setUserId(userEntity.getId());
                systemLogEntity.setIp(getIpAddress(request));
                systemLogEntity.setOperation(content);
                systemLogEntity.setContent(content);
                systemLogEntity.setCreateUserId(userEntity.getId());
                systemLogService.save(systemLogEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
