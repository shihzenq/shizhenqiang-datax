package com.wugui.dataxweb.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用于启动任务接收的实体
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName RunJobDto
 * @Version 1.0
 * @since 2019/6/27 16:12
 */
@Data
@ApiModel(value = "执行作业接受类")
public class RunJobDto implements Serializable {

    @NotNull(message = "作业id不能为空！")
    private Long jobManagerId;
}
