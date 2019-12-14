package com.wugui.dataxweb.dto.user;

import lombok.Data;

@Data
public class UserSearchDTO {

    private String username;

    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
