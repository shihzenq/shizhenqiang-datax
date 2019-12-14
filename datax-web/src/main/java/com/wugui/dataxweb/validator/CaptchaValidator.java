package com.wugui.dataxweb.validator;


import com.wugui.dataxweb.config.security.CaptchaService;
import com.wugui.dataxweb.validator.annotation.Captcha;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.wugui.dataxweb.config.Constants.CAPTCHA_ID_HEADER;

public class CaptchaValidator implements ConstraintValidator<Captcha, String> {

   private CaptchaService captchaService;

   private HttpServletRequest request;

   public void initialize(Captcha constraint) {
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      String captchaId = request.getHeader(CAPTCHA_ID_HEADER);
      return captchaService.verify(value, captchaId);
   }

   @Autowired
   public void setRequest(HttpServletRequest request) {
      this.request = request;
   }

   @Autowired
   public void setCaptchaService(CaptchaService captchaService) {
      this.captchaService = captchaService;
   }
}
