package com.wugui.dataxweb.service;

import com.alibaba.datax.common.log.LogResult;
import com.wugui.dataxweb.dto.RunJobDto;

import java.util.List;

/**
 * @program: datax-all
 * @author: huzekang
 * @create: 2019-06-17 11:25
 **/
public interface IDataxJobService {
    /**
     * 根据json字符串用线程池启动一个datax作业
     *
     * @param jobJson
     * @author: huzekang
     * @Date: 2019-06-17
     */
    List<com.alibaba.datax.core.DataXLog> startJobByJsonStr(String jobJson);

    List<com.alibaba.datax.core.DataXLog> startJobLog(RunJobDto runJobDto);

    LogResult viewJogLog(Long id, int fromLineNum);

    /**
     * 结束datax进程
     * @param pid
     * @param id
     * @return
     */
    Boolean killJob(String pid,Long id);

    String addExecutorLog(String ipAddress,List<com.alibaba.datax.core.DataXLog> logList);
}
