package com.wugui.dataxweb.dto.datasource;

import com.wugui.dataxweb.dto.ExistsForm;
import com.wugui.dataxweb.validator.group.Cheap;
import com.wugui.dataxweb.validator.group.OnAdd;
import com.wugui.dataxweb.validator.group.OnAddCheap;
import com.wugui.dataxweb.validator.group.Slot1AfterDefault;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("数据源新建连接")
public class DataSourceDTO implements ExistsForm {

    @NotEmpty(message = "连接名不能为空！")
    private String name;

    @NotEmpty(message = "类型不能为空！", groups = { Slot1AfterDefault.class })
    private String type;

    @NotEmpty(message = "ip地址不能为空！", groups = { OnAddCheap.class })
    private String ipAddr;

    private int port;

    @NotEmpty(message = "用户名不能为空！", groups = { Cheap.class })
    private String userName;

    @NotEmpty(message = "密码不能为空！", groups = { OnAdd.class })
    private String password;

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
