package com.wugui.dataxweb.util;

import org.springframework.validation.BindingResult;

public class BindingResultUtils {

    public static String getErrorMessage(BindingResult bindingResult) {
        String defaultMessage = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
        return defaultMessage;
    }
}
