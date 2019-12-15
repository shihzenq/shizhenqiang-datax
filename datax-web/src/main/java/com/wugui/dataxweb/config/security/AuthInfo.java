package com.wugui.dataxweb.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.wugui.dataxweb.config.Constants.ACCESS_KEY_VALIDITY_SECONDS;
import static com.wugui.dataxweb.config.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

/**
 * 工具类：操作登录/退出和进账套时候维护Redis中的账套ID和用户ID
 */
@Component
public class AuthInfo {

    private static final String ACCOUNT_SET_ID = "accountSetId";

    private static final String USER_ID = "userId";

    private StringRedisTemplate template;

    public void setUserId(String requestNo, Long userId) {
        setValue(requestNo, userId, USER_ID);
    }

    public Long getUserId(String requestNo) {
        return getValue(requestNo, USER_ID);
    }

    public void delete(String requestNo) {
        template.delete(requestNo);
    }

    private void setValue(String requestNo, Long value, String key) {
        String authInfo = template.opsForValue().get(requestNo);
        JSONObject object;
        if (StringUtil.isEmpty(authInfo)) {
            object = new JSONObject();
//            if (USER_ID.equals(key)) {
//                object.put(ACCOUNT_SET_ID, 0);
//                object.put(USER_ID, value);
//            } else {
//                object.put(ACCOUNT_SET_ID, value);
//                object.put(USER_ID, 0);
//            }
            object.put(USER_ID,value);
        } else {
            object = JSON.parseObject(authInfo);
            object.put(key, value);
        }
        // 设置超时时间（与登录token时效一致），避免无效数据长期保留在redis中
        template.opsForValue().set(requestNo, JSON.toJSONString(object), ACCESS_TOKEN_VALIDITY_SECONDS, TimeUnit.SECONDS);
    }

    private Long getValue(String requestNo, String key) {
        try {
            String authInfo = template.opsForValue().get(requestNo);
            if (!StringUtil.isEmpty(authInfo)) {
                // 用户活动监测，每次访问页面延长2h的免登陆时间
                template.opsForValue().set(requestNo, authInfo, ACCESS_KEY_VALIDITY_SECONDS, TimeUnit.SECONDS);
                JSONObject object = JSON.parseObject(authInfo);
                Object o = object.get(key);
                return Long.valueOf(o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
        return 0L;
    }

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }
}
