package com.wugui.dataxweb.dto.group;

import com.wugui.dataxweb.validator.group.OnAdd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "组名添加接受类")
public class GroupDTO {
    /**
     * 组名
     */
    @NotEmpty(message = "组名不能为空！", groups = { OnAdd.class })
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
