package com.wugui.dataxweb.dto.group;

import com.wugui.dataxweb.dto.ExistsForm;
import com.wugui.dataxweb.validator.group.OnAdd;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GroupDTO implements ExistsForm {
    /**
     * 组名
     */
    @NotEmpty(message = "组名不能为空！", groups = { OnAdd.class })
    private String name;

    /**
     * 备注
     */
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
