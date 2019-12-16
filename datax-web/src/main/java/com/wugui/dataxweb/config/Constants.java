package com.wugui.dataxweb.config;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 7*24*60*60;
    public static final String SIGNING_KEY = "devglan123r";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SESSION_TOKEN_KEY = "token";
    public static final String CAPTCHA_ID_HEADER = "Captcha-Request-Id";

    public static final String PROFIT_LIST_KEY = "profit-list";
    public static final String DATE_KEY = "date";
    public static final String PERIOD_TYPE = "period-type";
    public static final String ASSETS_DEBT_KEY = "assets-debt";
    public static final String RECLASS_KEY = "reClass";
    public static final String LOGOUT_LOG = "/logout/log";

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final long ACCESS_KEY_VALIDITY_SECONDS = 2*60*60;
    public static final String LOG_KEY = "zhongkeyuan-operate-log";// 操作日志在Redis中缓存的key值

    // JWT生成token时的密码
    public final static String TOKEN_SALT = "mW*RzA";
}
