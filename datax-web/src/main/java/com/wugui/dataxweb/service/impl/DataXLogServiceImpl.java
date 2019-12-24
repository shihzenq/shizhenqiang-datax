package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.DataXLogMapper;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.entity.JobManagerEntity;
import com.wugui.dataxweb.service.DataXLogService;
import com.wugui.dataxweb.service.JobManagerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataXLogServiceImpl extends ServiceImpl<DataXLogMapper, DataXLog> implements DataXLogService {

    private DataXLogMapper dataXLogMapper;

    private JobManagerService jobManagerService;

    @Override
    public DataXLog add(DataXLog dataXLog) {
        dataXLogMapper.insert(dataXLog);
        return dataXLog;
    }

    @Override
    public Boolean delete(Long id) {
        return dataXLogMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<DataXLog> list(Long userId, Integer pageNum, Integer pageSize) {
        QueryWrapper<DataXLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user_id", userId);
        PageHelper.startPage(pageNum, pageSize);
        List<DataXLog> dataXLogs = dataXLogMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(dataXLogs)) return null;
        for (DataXLog d : dataXLogs) {
            JobManagerEntity jobManagerEntity = jobManagerService.getById(d.getJobId());
            if (null == jobManagerEntity) continue;
            d.setUserName(jobManagerEntity.getCreateUserName());
            d.setJobName(jobManagerEntity.getJobName());
            d.setIp(jobManagerEntity.getTargetIp());
        }
        return new PageInfo<>(dataXLogs);
    }

    @Autowired
    public void setDataXLogMapper(DataXLogMapper dataXLogMapper) {
        this.dataXLogMapper = dataXLogMapper;
    }

    @Autowired
    public void setJobManagerService(JobManagerService jobManagerService) {
        this.jobManagerService = jobManagerService;
    }
}
