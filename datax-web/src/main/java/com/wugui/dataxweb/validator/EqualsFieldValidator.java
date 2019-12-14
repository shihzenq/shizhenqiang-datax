package com.wugui.dataxweb.validator;


import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsFieldValidator implements ConstraintValidator<EqualsField, Object> {

   private String source;
   private String target;

   public void initialize(EqualsField constraint) {
      source = constraint.source();
      target = constraint.target();
   }

   public boolean isValid(Object value, ConstraintValidatorContext context) {
      try {
         return BeanUtils.getProperty(value, source).equals(BeanUtils.getProperty(value, target));
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }
}
