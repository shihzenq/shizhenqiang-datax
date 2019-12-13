package com.wugui.dataxweb.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(WebMvcProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    private WebMvcProperties properties;

    public WebMvcConfiguration(WebMvcProperties properties) {
        this.properties = properties;
    }

    @Bean
    public PermissionInterceptor securityInterceptor() {
        return new PermissionInterceptor();
    }

    @Bean
    public LockInterceptor lockInterceptor() {
        return new LockInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(properties.getPathPattern())
                .exposedHeaders(properties.getExposedHeaders().toArray(new String[0]))
                .allowedHeaders(properties.getAllowedHeaders().toArray(new String[0]))
                .allowedMethods(properties.getAllowedMethods().toArray(new String[0]))
                .allowedOrigins(properties.getAllowedOrigins().toArray(new String[0]))
                .allowCredentials(properties.getAllowCredentials());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
                .excludePathPatterns("/login").excludePathPatterns("/micro-service/**")
                .addPathPatterns("/**");
        registry.addInterceptor(lockInterceptor())
                .addPathPatterns("/**");
    }
}
