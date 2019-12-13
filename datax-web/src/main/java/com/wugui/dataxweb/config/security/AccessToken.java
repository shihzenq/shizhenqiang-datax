package com.wugui.dataxweb.config.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
    private String scope;
    private String name;
    private Long userId;
    private String username;
    private String jti;
}

