package com.wugui.dataxweb.validator;

import com.wugui.dataxweb.dto.ExistsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<com.wugui.dataxweb.validator.annotation.Exists, ExistsForm> {

    private Exists service;

    private String field;

    private String mappingField;

    private boolean excludeSelf;

    private boolean exists;

    @Autowired
    private ApplicationContext appContext;

    /**
     * @Description:
     * @author: shizhenqiang
     * @date:
     * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
     * @param constraintAnnotation 约束声明的约束注释
     */

    @Override
    public void initialize(com.wugui.dataxweb.validator.annotation.Exists constraintAnnotation) {
        service = appContext.getBean(constraintAnnotation.serviceClass());
        field = constraintAnnotation.field();
        mappingField = constraintAnnotation.mappingField();
        excludeSelf = constraintAnnotation.excludeSelf();
        exists = constraintAnnotation.exists();
        service.setGroups(constraintAnnotation.groups());
    }


    @Override
    public boolean isValid(ExistsForm form, ConstraintValidatorContext constraintValidatorContext) {
        boolean result;
        String f = "".equals(mappingField) ? field : mappingField;
        if (excludeSelf) {
            result = service.checkExists(f, form.getUniqueFieldValue(field), form.getPrimaryKey(), form, form.getOtherParams());
        } else {
            result = service.checkExists(f, form.getUniqueFieldValue(field), null, form, form.getOtherParams());
        }
        if (exists) { // true:必须存在，校验通过, false:必须不存在，校验通过
            return result;
        } else {
            return !result;
        }
    }
}
