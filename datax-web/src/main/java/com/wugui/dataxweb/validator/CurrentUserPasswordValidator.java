package com.wugui.dataxweb.validator;

import com.wugui.dataxweb.config.security.IAuthenticationFacade;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.validator.annotation.CurrentUserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentUserPasswordValidator implements ConstraintValidator<CurrentUserPassword, String> {

    private IAuthenticationFacade authenticationFacade;

    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(CurrentUserPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity user = authenticationFacade.getUser();
        return passwordEncoder.matches(value, user.getPasswordHash());
    }

    @Autowired
    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
