package com.wugui.dataxweb.config;

import com.wugui.dataxweb.config.security.PermissionInterceptor;
import com.wugui.dataxweb.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/login", "/user/registerUser","/home/invite/**",
                		"/invitation/**","/img/**","/css/**","/js/**","/html/**","/actuator/**")
                //饶过swagger
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**", "/static/index.html", "/**",  "/static/**");


        registry.addInterceptor(permissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/login", "/user/registerUser","/home/invite/**",
                        "/invitation/**","/img/**","/css/**","/js/**","/html/**","/actuator/**")
                //饶过swagger
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**", "/static/index.html", "/static/**");

    }

}

