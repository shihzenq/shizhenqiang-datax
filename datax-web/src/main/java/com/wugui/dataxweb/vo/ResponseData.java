package com.wugui.dataxweb.vo;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {
    private T data;
    private String message;
    private Integer code;
    private static final long serialVersionUID = 1L;

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
