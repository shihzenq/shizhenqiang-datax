package com.wugui.dataxweb.validator.annotation;

import com.wugui.dataxweb.validator.PattensValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PattensValidator.class)
@Documented
public @interface Patterns {
    String message() default "PattensValidator error";

    Class<?>[] groups() default { };

    String pattern(); // phone, email, companyNo, chinese, idCard, tel, telOrPhone, password, number

    Class<? extends Payload>[] payload() default { };

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Patterns[] value();
    }
}