package com.wugui.dataxweb.dto.role;

import com.wugui.dataxweb.validator.group.Cheap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "基于角色修改权限")
public class RoleUpdatePermissionDTO implements Serializable {

    @NotNull(message = "角色id不能为空！")
    private Long roleId;


    @NotNull(message = "权限id不能为空!", groups = Cheap.class)
    @Size(min = 1, message = "权限id至少为一条!")
    @ApiModelProperty(value = "权限id", required = true)
    private List<Long> permissionList = new ArrayList<>();
}
