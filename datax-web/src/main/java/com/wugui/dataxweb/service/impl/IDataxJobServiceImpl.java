package com.wugui.dataxweb.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.common.log.EtlJobLogger;
import com.alibaba.datax.common.log.LogResult;
import com.alibaba.datax.common.spi.ErrorCode;
import com.alibaba.datax.core.Engine;
import com.alibaba.datax.core.util.ExceptionTracker;
import com.alibaba.datax.core.util.FrameworkErrorCode;
import com.alibaba.datax.core.util.container.CoreConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wugui.dataxweb.dto.RunJobDto;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.entity.JobLog;
import com.wugui.dataxweb.service.IDataxJobService;
import com.wugui.dataxweb.service.IJobLogService;
import com.wugui.dataxweb.util.ProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: datax-all
 * @author: huzekang
 * @create: 2019-06-17 11:26
 **/
@Slf4j
@Service
public class IDataxJobServiceImpl implements IDataxJobService {

    private final static ConcurrentMap<String, String> jobTmpFiles = Maps.newConcurrentMap();

    private static final Logger log = LoggerFactory.getLogger(IDataxJobServiceImpl.class);

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("datax-job-%d").build();

    private ExecutorService jobPool = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 日志文件保存目录
     */
    @Value("${app.etlLogDir}")
    private String etlLogDir;

    @Value("${dataX-path}")
    private String dataXPath;

    @Autowired
    private IJobLogService jobLogService;


    @Override
    public List<com.alibaba.datax.core.DataXLog> startJobByJsonStr(String jobJson) {
        List<com.alibaba.datax.core.DataXLog> logList = new ArrayList<>();
        jobPool.submit(() -> {

            final String tmpFilePath = "/xcloud/gpDataTest/temp_json/jobTmp-" + System.currentTimeMillis() + ".json";
            // 根据json写入到临时本地文件
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(tmpFilePath, "UTF-8");
                writer.println(jobJson);

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }

            try {
                // 使用临时本地文件启动datax作业
                com.alibaba.datax.core.DataXLog log = Engine.entry(tmpFilePath);
                FileUtil.del(new File(tmpFilePath));
                // 执行日志
                if (null != log) {
                    logList.add(log);
                }
                //  删除临时文件
            } catch (Throwable e) {
                log.error("\n\n经DataX智能分析,该任务最可能的错误原因是:\n" + ExceptionTracker.trace(e));

                if (e instanceof DataXException) {
                    DataXException tempException = (DataXException) e;
                    ErrorCode errorCode = tempException.getErrorCode();
                    if (errorCode instanceof FrameworkErrorCode) {
                        FrameworkErrorCode tempErrorCode = (FrameworkErrorCode) errorCode;
                    }
                }

            }
        });

        return logList;
    }


    @Override
    public List<com.alibaba.datax.core.DataXLog> startJobLog(RunJobDto runJobDto) {
        //取出 jobJson，并转为json对象
        JSONObject json = JSONObject.parseObject(runJobDto.getJobJson());
        //根据jobId和当前时间戳生成日志文件名
        String logFileName = runJobDto.getJobConfigId().toString().concat("_").concat(StrUtil.toString(System.currentTimeMillis()).concat(".log"));
        String logFilePath = etlLogDir.concat(logFileName);
        //记录日志
        JobLog jobLog = new JobLog();
        jobLog.setJobId(runJobDto.getJobConfigId());
        jobLog.setLogFilePath(logFilePath);
        jobLogService.save(jobLog);
        //将路径放进去
        json.put(CoreConstant.LOG_FILE_PATH, logFilePath);

        //启动任务
        return startJobByJsonStr(JSON.toJSONString(json));
    }

    @Override
    public LogResult viewJogLog(Long id, int fromLineNum) {
        QueryWrapper<JobLog> queryWrapper = new QueryWrapper<>();
        //根据id获取最新的日志文件路径
        queryWrapper.lambda().eq(JobLog::getJobId, id).orderByDesc(JobLog::getCreateDate);
        List<JobLog> list = jobLogService.list(queryWrapper);
        //取最新的一条记录
        if (list.isEmpty()) {
            return new LogResult(1, 1, "没有找到对应的日志文件！", true);
        } else {
            //取出路径，读取文件
            return EtlJobLogger.readLog(list.get(0).getLogFilePath(), fromLineNum);
        }
    }

    @Override
    public Boolean killJob(String pid, Long id) {
        boolean result = ProcessUtil.killProcessByPid(pid);
        //  删除临时文件
        if (!CollectionUtils.isEmpty(jobTmpFiles)) {
            String pathname = jobTmpFiles.get(pid);
            if (pathname != null) {
                FileUtil.del(new File(pathname));
            }
        }
        //jobLogService.update(null, Wrappers.<JobLog>lambdaUpdate().set(JobLog::getHandleMsg, "job running，killed").set(JobLog::getHandleCode, 500).eq(JobLog::getId, id));
        return result;
    }

    @Override
    public String addExecutorLog(String ipAddress, List<com.alibaba.datax.core.DataXLog> logList) {
        if (!CollectionUtils.isEmpty(logList)) {
            com.alibaba.datax.core.DataXLog source = new com.alibaba.datax.core.DataXLog();
            for (com.alibaba.datax.core.DataXLog log : logList) {
                if (null != log) {
                    source = log;
                }
            }
            if (null != source) {
                // 执行日志插入
                DataXLog dataXLog = new DataXLog();
                BeanUtils.copyProperties(source, dataXLog);

            }
        }
        return null;
    }
}
