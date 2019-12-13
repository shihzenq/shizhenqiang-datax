package com.wugui.dataxweb.config.security;

import java.lang.annotation.*;

/**
 * 用于自定义权限控制，使用在控制器类或控制器方法上
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {

    String value() default "";

    // 多个权限或运算，只要有其中一个即可
    String[] values() default {};
}
