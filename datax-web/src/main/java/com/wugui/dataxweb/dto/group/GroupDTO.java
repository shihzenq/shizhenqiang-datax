package com.wugui.dataxweb.dto.group;

import com.wugui.dataxweb.dto.ExistsForm;
import com.wugui.dataxweb.validator.group.OnAdd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "组名添加接受类")
public class GroupDTO implements ExistsForm {
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

    @Override
    public String getPrimaryKey() {
        return null;
    }

    @Override
    public String getUniqueFieldValue(String field) {
        return null;
    }

    @Override
    public String[] getOtherParams() {
        return new String[0];
    }
}
