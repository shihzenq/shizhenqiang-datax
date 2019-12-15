package com.wugui.dataxweb.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout/do").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.logout().logoutUrl("/logout/do").logoutSuccessHandler(logoutSuccessHandler());

//        http.authorizeRequests().anyRequest().permitAll().and().formLogin().loginPage("/login").defaultSuccessUrl("/").successForwardUrl("/index2").failureForwardUrl("/fail").permitAll()
//        .and().logout().permitAll()
//         // 设置拒绝访问的提示URI
//        .and().exceptionHandling().accessDeniedPage("/login?illegal")
//        .and().csrf().disable().anonymous().disable();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean("passwordEncoder")
    public PasswordEncoder encoder() {
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();
        // {BCrypt}
        encoderMap.put("BCrypt", new BCryptPasswordEncoder());
        // {SHA-256}
        encoderMap.put("SHA-256", new StandardPasswordEncoder());
        // 目前使用  SHA-256
        return new DelegatingPasswordEncoder("BCrypt", encoderMap);
    }
}
