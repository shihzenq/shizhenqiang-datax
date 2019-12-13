package com.wugui.dataxweb.config.security;

import com.wugui.dataxweb.entity.UserEntity;

import java.io.IOException;

public interface IAuthenticationFacade {
    UserEntity getUser();
    CurrentUserDetails getCurrentUser() throws IOException;
}
