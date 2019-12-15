package com.wugui.dataxweb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchDTO implements Serializable {

    @ApiModelProperty(value = "页数")
    private Integer pageNum;

    @ApiModelProperty(value = "条数")
    private Integer pageSize;


    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
