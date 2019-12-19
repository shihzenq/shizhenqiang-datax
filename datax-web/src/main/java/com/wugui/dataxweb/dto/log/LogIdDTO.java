package com.wugui.dataxweb.dto.log;

import com.wugui.dataxweb.validator.group.OnUpdateCheap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "日志id参数接受类")
public class LogIdDTO {
    @ApiModelProperty(value = "日志id", notes = "日志id")
    @NotNull(message = "日志id不能为空！", groups = OnUpdateCheap.class)
    private Long id;
}
