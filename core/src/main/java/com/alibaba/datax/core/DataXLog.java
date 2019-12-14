package com.alibaba.datax.core;


public class DataXLog {
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

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(Long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public Long getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(Long totalCosts) {
        this.totalCosts = totalCosts;
    }

    public Long getByteSpeedPerSecond() {
        return byteSpeedPerSecond;
    }

    public void setByteSpeedPerSecond(Long byteSpeedPerSecond) {
        this.byteSpeedPerSecond = byteSpeedPerSecond;
    }

    public Long getRecordSpeedPerSecond() {
        return recordSpeedPerSecond;
    }

    public void setRecordSpeedPerSecond(Long recordSpeedPerSecond) {
        this.recordSpeedPerSecond = recordSpeedPerSecond;
    }

    public Long getTotalReadRecords() {
        return totalReadRecords;
    }

    public void setTotalReadRecords(Long totalReadRecords) {
        this.totalReadRecords = totalReadRecords;
    }

    public Long getTotalErrorRecords() {
        return totalErrorRecords;
    }

    public void setTotalErrorRecords(Long totalErrorRecords) {
        this.totalErrorRecords = totalErrorRecords;
    }

    public Long getTransformerSucceedRecords() {
        return transformerSucceedRecords;
    }

    public void setTransformerSucceedRecords(Long transformerSucceedRecords) {
        this.transformerSucceedRecords = transformerSucceedRecords;
    }

    public Long getTransformerFailedRecords() {
        return transformerFailedRecords;
    }

    public void setTransformerFailedRecords(Long transformerFailedRecords) {
        this.transformerFailedRecords = transformerFailedRecords;
    }

    public Long getTransformerFilterRecords() {
        return transformerFilterRecords;
    }

    public void setTransformerFilterRecords(Long transformerFilterRecords) {
        this.transformerFilterRecords = transformerFilterRecords;
    }
}
