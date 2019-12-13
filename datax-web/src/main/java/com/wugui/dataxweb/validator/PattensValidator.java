package com.wugui.dataxweb.validator;

import com.wugui.dataxweb.validator.annotation.Patterns;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PattensValidator implements ConstraintValidator<Patterns, String> {

    /**
     * phone, email, companyNo, chinese, idCard, tel, telOrPhone
     */
    private String pattern = null;

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^1\\d{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    private static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]*$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";

    /**
     * 正则表达式：企业营业执照统一社会信用代码正则
     */
    private static final String REGEX_COMPANY_NO = "^[A-Za-z0-9]{18}$";

    /**
     * 正则表达式：固定电话
     */
    private static final String REGEX_TEL = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";

    /**
     * 正则表达式：QQ号码
     */
    private static final String QQ = "^\\d+$";

    /**
     * 数字
     */
    private static final String NUMBER = "^\\d+$";

    /**
     * 正则表达式密码
     */
    private static final String PASSWORD = "^[0-9a-zA-Z`~!@#$%^&*()+=|{}':;,\\\\.<>/?_\\- ]*$";

    public void initialize(Patterns constraint) {
        this.pattern = constraint.pattern();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(null == value || (value = value.trim()).equals("")) return true;
        String p = "";
        switch (pattern){
            case "phone":
                p = REGEX_MOBILE;
                break;
            case "tel":
                p = REGEX_TEL;
                break;
            case "telOrPhone":
                p = "telOrPhone";
                break;
            case "email":
                p = REGEX_EMAIL;
                break;
            case "unifiedCode":
                p = REGEX_COMPANY_NO;
                break;
            case "chinese":
                p = REGEX_CHINESE;
                break;
            case "legalPersonNo":
                p = REGEX_ID_CARD;
                break;
            case "qq":
                p = QQ;
                break;
            case "password":
                p = PASSWORD;
                break;
            case "number":
                p = NUMBER;
                break;
        }
        if(p.equals("telOrPhone")){
            Pattern pattern1 = Pattern.compile(REGEX_MOBILE);
            Pattern pattern2 = Pattern.compile(REGEX_TEL);
            Matcher matcher1 = pattern1.matcher(value);
            Matcher matcher2 = pattern2.matcher(value);
            return matcher1.matches() || matcher2.matches();
        }
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
