package com.wugui.dataxweb.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wugui.dataxweb.config.Constants;
import com.wugui.dataxweb.entity.OperateLog;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.log.LogConstant;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.IpUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    private RestTemplate restTemplate;

    private ResourceServerProperties resourceServerProperties;

    private StringRedisTemplate template;

    private AuthInfo authInfo;

    private HttpServletRequest request;

    private AuthenticationFacade facade;

    @Value("${init-password}")
    private String password;

    @Autowired
    private UserService userService;

    public AccessToken getAccessToken(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        String plainCredentials = resourceServerProperties.getClientId() + ":" + resourceServerProperties.getClientSecret();
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, requestHeaders);
        AccessToken accessToken = null;
        try {
            accessToken = restTemplate.postForObject("//AUTH-SERVER/oauth/token", httpEntity, AccessToken.class);
        } catch (HttpClientErrorException e) {
            if(e.getRawStatusCode() == 401) {
                throw new UsernameNotFoundException("当前用户手机号不存在，请重新输入！");
            } else if (e.getRawStatusCode() == 400) {
                throw new BadCredentialsException("用户登录密码输入错误，请重新输入！");
            }
        }
        return accessToken;
    }

    @Override
    public String logout(String token) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + token);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("token", token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, requestHeaders);
        try {
            CurrentUserDetails currentUser = facade.getCurrentUser();
            UserEntity enterpriseUser = userService.getByUsername(currentUser.getUsername());
            OperateLog log = new OperateLog();
            log.setOperateTime(new Date());
            if (enterpriseUser != null) {
                log.setOperateUserId(enterpriseUser.getId());
                log.setOperateUserName(enterpriseUser.getName().length() > 5 ?
                        enterpriseUser.getName().substring(0, 5) : enterpriseUser.getName());
                log.setOperateUserUsername(enterpriseUser.getUsername());
            } else {
                log.setOperateUserId(0L);
                log.setOperateUserName("");
                log.setOperateUserUsername("");
                log.setEnterpriseId(0L);
            }
            log.setPath("系统退出");
            log.setType(LogConstant.TYPE_LOGOUT);
            log.setContent("系统退出");

            log.setOperateIp(IpUtils.getIpAddress(request));
            log.setAccountSetId(0L);
            log.setScope((short) 2);
            template.opsForList().rightPush(Constants.LOG_KEY, JSON.toJSONString(log));

            authInfo.delete(currentUser.getJti());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restTemplate.postForObject("//AUTH-SERVER/oauth/logout", httpEntity, String.class, map);
    }

    public boolean addUser(UserEntity enterpriseUser) {
        Map map = restTemplate.postForObject("//AUTH-SERVER/user/create", packageUser(enterpriseUser, password), Map.class);
        assert map != null;
        if ((Boolean) map.get("result")) {
            enterpriseUser.setUcUid(Long.valueOf(String.valueOf(map.get("value"))));
        }
        return true;
    }

    @Override
    public boolean addUser(UserEntity accountSetUser, String password) {
        Map map= packageUser(accountSetUser, password);
        Map res = restTemplate.postForObject("//AUTH-SERVER/user/create", map, Map.class);
        assert res != null;
        if ((Boolean) res.get("result")) {
            accountSetUser.setUcUid(Long.valueOf(res.get("value").toString()));
        }
        return false;
    }


    public boolean updateUser(UserEntity enterpriseUser, String password) {
        Map map = restTemplate.postForObject("//AUTH-SERVER/user/update", packageUser(enterpriseUser, password), Map.class);
        assert map != null;
        if ((Boolean) map.get("result")) {
            enterpriseUser.setUcUid(Long.valueOf(String.valueOf(map.get("value"))));
        }
        return true;
    }

    @Override
    public boolean deleteUser(UserEntity enterpriseUser) {
        return status(enterpriseUser, (short)0, false);
    }

    @Override
    public boolean status(UserEntity enterpriseUser, Short status, Boolean onJob) {
        Map<String, Object> data= new HashMap<>();
        data.put("id", enterpriseUser.getUcUid());
        data.put("username", enterpriseUser.getUsername());
        data.put("personStatus", status);
        data.put("onJob", true); // erp当前暂时不支持离职状态后返聘
//        data.put("outId", "wkxz-" + enterpriseUser.getEnterpriseId());
        JSONObject res = restTemplate.postForObject("//AUTH-SERVER/user/person-status", data, JSONObject.class);
        return res != null && res.getBoolean("result");
    }

    private Map packageUser(UserEntity enterpriseUser, String password) {
        return packageUser(enterpriseUser.getUcUid(), enterpriseUser.getUsername(), enterpriseUser.getPhone(),
                enterpriseUser.getName(), enterpriseUser.getName(), password);
    }

    private Map packageUser(Long ucUid, String username, String phone, String name, String nickname, String password) {
        Map<String, Object> map= new HashMap<>();
        if (ucUid != null && ucUid > 0) {
            map.put("id", ucUid);
        }
        map.put("username", username);
        map.put("phone", phone);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("password", password);
//        map.put("outId", "".equals(outId) ? "" : "wkxz-" + outId);
        return map;
    }

    @Autowired
    public void setResourceServerProperties(ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setFacade(AuthenticationFacade facade) {
        this.facade = facade;
    }
}