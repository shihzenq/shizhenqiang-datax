package com.wugui.dataxweb.service;

import com.alibaba.datax.common.log.LogResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.entity.JobManagerEntity;

import java.util.List;

/**
 * @program: datax-all
 * @author: huzekang
 * @create: 2019-06-17 11:25
 **/
public interface IDataxJobService extends IService<DataXLog> {
    /**
     * 根据json字符串用线程池启动一个datax作业
     *
     * @param jobJson
     * @author: huzekang
     * @Date: 2019-06-17
     */
    List<com.alibaba.datax.core.DataXLog> startJobByJsonStr(String jobJson, String ipAddress, Long jobManagerId);

//    List<com.alibaba.datax.core.DataXLog> startJobLog(RunJobDto runJobDto);
    List<com.alibaba.datax.core.DataXLog> startJobLog(JobManagerEntity runJobDto, String ipAddress);

    LogResult viewJogLog(Long id, int fromLineNum);

    /**
     * 结束datax进程
     * @param pid
     * @param id
     * @return
     */
    Boolean killJob(String pid,Long id);

    Boolean addExecutorLog(String ipAddress, com.alibaba.datax.core.DataXLog log, Long jobId);
}
