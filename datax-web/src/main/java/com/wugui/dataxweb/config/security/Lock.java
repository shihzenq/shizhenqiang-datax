package com.wugui.dataxweb.config.security;

import java.lang.annotation.*;

/**
 * 分布式锁
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lock {
    long expireTime() default 60000;
}
