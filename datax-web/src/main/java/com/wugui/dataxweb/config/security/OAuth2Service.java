package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.entity.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface OAuth2Service {
    AccessToken getAccessToken(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;

    String logout(String token);

    boolean addUser(UserEntity enterpriseUser);

    boolean addUser(UserEntity accountSetUser, String password);


    boolean updateUser(UserEntity enterpriseUser, String password);

    boolean deleteUser(UserEntity enterpriseUser);

    boolean status(UserEntity enterpriseUser, Short status, Boolean onJob);
}