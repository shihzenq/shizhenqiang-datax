package com.wugui.dataxweb.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "zhongkeyuan.webmvc.cross")
public class WebMvcProperties {

    private String pathPattern;
    private List<String> allowedHeaders;
    private List<String> allowedMethods;
    private List<String> allowedOrigins;
    private Boolean allowCredentials;
    private List<String> exposedHeaders;

    public String getPathPattern() {
        return pathPattern == null ? "/**" : pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders == null ? getDefaults() : allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods == null ? getDefaults() : allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins == null ? getDefaults() : allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials == null ? false : allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public List<String> getExposedHeaders() {
        return exposedHeaders == null ? getDefaults("") : exposedHeaders;
    }

    public void setExposedHeaders(List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    private List<String> getDefaults(String value) {
        List<String> defaults = new ArrayList<>();
        defaults.add(value);
        return defaults;
    }

    private List<String> getDefaults() {
        return getDefaults("*");
    }
}
