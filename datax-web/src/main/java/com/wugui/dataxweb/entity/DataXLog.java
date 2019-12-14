package com.wugui.dataxweb.entity;

import lombok.Data;

@Data
public class DataXLog extends BaseEntity {
    //任务启动时刻
    private Long startTimeStamp;
    //任务结束时刻
    private Long endTimeStamp;
    //任务总时耗
    private Long totalCosts;
    //任务平均流量
    private Long byteSpeedPerSecond;
    //记录写入速度
    private Long recordSpeedPerSecond;
    //读出记录总数
    private Long totalReadRecords;
    //读写失败总数
    private Long totalErrorRecords;
    //成功记录总数
    private Long transformerSucceedRecords;
    // 失败记录总数
    private Long transformerFailedRecords;
    // 过滤记录总数
    private Long transformerFilterRecords;
}
