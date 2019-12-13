package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.JobDataSourceMapper;
import com.wugui.dataxweb.entity.JobDataSourceEntity;
import com.wugui.dataxweb.service.JobDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDataSourceServiceImpl extends ServiceImpl<JobDataSourceMapper, JobDataSourceEntity> implements JobDataSourceService {

    private JobDataSourceMapper jobDataSourceMapper;

    @Override
    public JobDataSourceEntity add(JobDataSourceEntity jobDataSourceEntity) {
        jobDataSourceMapper.insert(jobDataSourceEntity);
        return jobDataSourceEntity;
    }

    @Override
    public boolean update(JobDataSourceEntity jobDataSourceEntity) {
//        jobDataSourceMapper.update(jobDataSourceEntity);
        return false;
    }

    @Override
    public List<JobDataSourceEntity> selectAll(Long userId) {
        return null;
    }

    @Autowired
    public void setJobDataSourceMapper(JobDataSourceMapper jobDataSourceMapper) {
        this.jobDataSourceMapper = jobDataSourceMapper;
    }
}
