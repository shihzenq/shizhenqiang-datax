package com.wugui.dataxweb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@ApiModel(value = "dataX日志类")
@Table(name = "data_x_log")
public class DataXLog extends BaseEntity {

    @ApiModelProperty(value = "作业数据id")
    private Long jobId;

    //任务启动时刻
    @ApiModelProperty(value = "任务启动时刻")
    private Long startTimeStamp;

    //任务结束时刻
    @ApiModelProperty(value = "任务结束时刻")
    private Long endTimeStamp;

    //任务总时耗
    @ApiModelProperty(value = "任务总时耗")
    private Long totalCosts;
    //任务平均流量
    @ApiModelProperty(value = "任务平均流量")
    private Long byteSpeedPerSecond;

    //记录写入速度
    @ApiModelProperty(value = "记录写入速度")
    private Long recordSpeedPerSecond;

    //读出记录总数
    @ApiModelProperty(value = "读出记录总数")
    private Long totalReadRecords;

    //读写失败总数
    @ApiModelProperty(value = "读写失败总数")
    private Long totalErrorRecords;

    //成功记录总数
//    @ApiModelProperty(value = "成功记录总数")
//    @TableName(value = "transformer_succeed_records")
//    private Long transformerSucceedRecords;
//    // 失败记录总数
//    @ApiModelProperty(value = "失败记录总数")
//    private Long transformerFailedRecords;
//
//    // 过滤记录总数
//    @ApiModelProperty(value = "过滤记录总数")
//    @TableName(value = "")
//    private Long transformerFilterRecords;

    private String userName;
}
