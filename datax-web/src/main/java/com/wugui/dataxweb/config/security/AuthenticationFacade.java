package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    private UserService userService;

    public UserEntity getUser() {
        CurrentUserDetails currentUser;
        try {
            currentUser = getCurrentUser();
        } catch (Exception e) {
            return null;
        }
        return userService.getByUsername(currentUser.getUsername());
    }

    public CurrentUserDetails getCurrentUser() throws IOException {
        // AnonymousAuthenticationToken
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new IOException("No Authentication"); // 有待研究
        }
        OAuth2Authentication authentication = (OAuth2Authentication) auth;
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        String claims = JwtHelper.decode(tokenValue).getClaims();
        ObjectMapper om = new ObjectMapper();
        Map map = om.readValue(claims, Map.class);
        String username = (String) map.get("username");
        Long userId = Long.valueOf(map.get("userId").toString());
        String name = (String) map.get("name");
        String jti = (String) map.get("jti");
        CurrentUserDetails userDetails = new CurrentUserDetails();
        userDetails.setUsername(username);
        userDetails.setName(name);
        userDetails.setUserId(userId);
        userDetails.setJti(jti);
        return userDetails;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}