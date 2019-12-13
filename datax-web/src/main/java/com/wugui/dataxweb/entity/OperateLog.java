package com.wugui.dataxweb.entity;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable {

    /* 范围-通用日志 */
    public static final Short SCOPE_NORMAL = 0;
    /* 范围-账套内日志 */
    public static final Short SCOPE_ACCOUNT_SET_INSIDE = 1;
    /* 范围-账套外日志 */
    public static final Short SCOPE_ACCOUNT_SET_OUTSIDE = 2;

    private Long id;

    private Date operateTime;

    private Long operateUserId;

    private String operateUserName;

    private String operateUserUsername;

    private String path;

    private Short type;

    private String content;

    private String operateIp;

    private Long enterpriseId;

    private Long accountSetId;

    private Short scope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName == null ? null : operateUserName.trim();
    }

    public String getOperateUserUsername() {
        return operateUserUsername;
    }

    public void setOperateUserUsername(String operateUserUsername) {
        this.operateUserUsername = operateUserUsername == null ? null : operateUserUsername.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getAccountSetId() {
        return accountSetId;
    }

    public void setAccountSetId(Long accountSetId) {
        this.accountSetId = accountSetId;
    }

    public Short getScope() {
        return scope;
    }

    public void setScope(Short scope) {
        this.scope = scope;
    }
}