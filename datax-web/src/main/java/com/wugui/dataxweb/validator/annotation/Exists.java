package com.wugui.dataxweb.validator.annotation;


import com.wugui.dataxweb.validator.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
@Documented
public @interface Exists {

    String message() default "值已经存在";

    Class<?>[] groups() default { };

    Class<? extends com.wugui.dataxweb.validator.Exists> serviceClass();

    Class<? extends Payload>[] payload() default { };

    String field();

    /**
     * 校验映射字段，在实际校验器中对应的字段名称，比如一个外键为assist_id,实际检查的时候需要检查assist表中的id字段，所以，在field设置为assist_id,mappingField设置为id
     * @return String
     */
    String mappingField() default "";

    /**
     * 想要得到的结果：
     * true:必须存在，校验通过
     * false:必须不存在，校验通过
     */
    boolean exists() default true;

    // 是否排除自身
    boolean excludeSelf() default true;

    @Target({ TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Exists[] value();
    }
}
