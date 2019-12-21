package com.wugui.dataxweb.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JobLogIdDTO {

    @ApiModelProperty(value = "作业数据id")
    private Long id;
}
