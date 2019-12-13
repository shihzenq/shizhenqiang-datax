package com.wugui.dataxweb.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    String value() default "";
    /** 日志类型 */
    short type() default (short) 0;
    /** 模块路径 */
    String path() default "";
    /** 日志内容 */
    String content() default "";
    /** 日志范围：0通用，1账套内，2账套外*/
    short scope() default 0;
}
