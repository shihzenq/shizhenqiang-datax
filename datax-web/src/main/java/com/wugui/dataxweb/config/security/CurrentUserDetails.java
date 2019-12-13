package com.wugui.dataxweb.config.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrentUserDetails {
    private Long userId;
    private String username;
    private String name;
    private String jti;
}
