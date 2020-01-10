package com.wugui.dataxweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "job_manager_entity")
@ApiModel(value = "作业配置实体类")
public class JobManagerEntity extends BaseEntity {

    @ApiModelProperty(value = "作业名")
    private String jobName;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "组id")
    private Long groupId;

    @ApiModelProperty(value = "输入端ip")
    private String sourceIp;

    @ApiModelProperty(value = "输出端ip")
    private String targetIp;

    @ApiModelProperty(value = "状态, 0:未开启，1：进行中，2：已完成")
    private Integer status = 0;

    @ApiModelProperty(value = "创建人名字")
    private String createUserName;

    @ApiModelProperty(value = "执行的json字符串")
    private String jobJson;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "读取的数据源id")
    private Long sourceId;

    @ApiModelProperty(value = "写入的数据源id")
    private Long targetId;

    @TableField(exist = false)
    @ApiModelProperty(value = "读取原数据源id")
    private Long readerDatasourceId;

    @TableField(exist = false)
    @ApiModelProperty(value = "读取原数据源库下表名")
    private List<String> readerTables;

    @TableField(exist = false)
    @ApiModelProperty(value = "读取原数据源库下表下的字段")
    private List<String> readerColumns;

    @TableField(exist = false)
    private Boolean ifStreamWriter = false;

    @TableField(exist = false)
    @ApiModelProperty(value = "往目标数据源写的id")
    private Long writerDatasourceId;

    @TableField(exist = false)
    @ApiModelProperty(value = "往目标数据源写的表名")
    private List<String> writerTables;

    @TableField(exist = false)
    @ApiModelProperty(value = "往目标数据源写的字段名名")
    private List<String> writerColumns;

    @TableField(exist = false)
    @ApiModelProperty(value = "查询的SQL")
    private String querySql ="";

    @TableField(exist = false)
    @ApiModelProperty(value = "执行的SQL")
    private String preSql = "";
}
