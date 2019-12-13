package com.wugui.dataxweb.config.security;

import com.alibaba.fastjson.JSON;
import com.wugui.dataxweb.config.Constants;
import com.wugui.dataxweb.entity.OperateLog;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.wugui.dataxweb.config.Constants.LOGOUT_LOG;
import static com.wugui.dataxweb.log.LogConstant.TYPE_LOGOUT;

@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    private StringRedisTemplate template;

    private AuthInfo authInfo;

    @Autowired
    private UserService userService;

    private IAuthenticationFacade authenticationFacade;

    @SuppressWarnings("all")
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CurrentUserDetails currentUser = authenticationFacade.getCurrentUser();
        String requestNo = currentUser.getJti();
        Long userId = authInfo.getUserId(requestNo);

        // 记录退出日志
        UserEntity enterpriseUser = userService.get(Long.valueOf(userId));
        OperateLog log = new OperateLog();
        log.setOperateTime(new Date());
        if (enterpriseUser != null) {
            log.setOperateUserId(enterpriseUser.getId());
            log.setOperateUserName(enterpriseUser.getName().length() > 5 ?
                    enterpriseUser.getName().substring(0, 5) : enterpriseUser.getName());
            log.setOperateUserUsername(enterpriseUser.getUsername());
        } else {
            log.setOperateUserId(0L);
            log.setOperateUserName("");
            log.setOperateUserUsername("");
            log.setEnterpriseId(0L);
        }
        log.setPath("系统退出");
        log.setType(TYPE_LOGOUT);
        log.setContent("系统退出");
        log.setOperateIp(IpUtils.getIpAddress(request));
        log.setAccountSetId(0L);
        log.setScope((short) 2);
        template.opsForList().rightPush(Constants.LOG_KEY, JSON.toJSONString(log));

        authInfo.delete(requestNo);

        request.getRequestDispatcher(LOGOUT_LOG).forward(request, response);
    }



    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Autowired
    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }
}
