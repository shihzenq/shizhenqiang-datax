package com.wugui.dataxweb.dto.group;

import com.wugui.dataxweb.dto.SearchDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "组名查询接受类")
public class GroupSearchDTO extends SearchDTO {

    @ApiModelProperty(value = "组名")
    private String name;
}
