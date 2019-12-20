package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.JobManagerMapper;
import com.wugui.dataxweb.dto.job.JobManagerDTO;
import com.wugui.dataxweb.entity.JobManagerEntity;
import com.wugui.dataxweb.service.JobManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobManagerServiceImpl extends ServiceImpl<JobManagerMapper, JobManagerEntity> implements JobManagerService {

    @Autowired
    private JobManagerMapper jobManagerMapper;

    @Override
    public PageInfo<JobManagerEntity> list(JobManagerDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<JobManagerEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getJobName()) && StringUtils.isNotBlank(dto.getGroupName())) {
            queryWrapper.eq("job_name", dto.getJobName());
            queryWrapper.eq("group_name", dto.getGroupName());
        } else if (StringUtils.isNotBlank(dto.getJobName()) && StringUtils.isBlank(dto.getGroupName())) {
            queryWrapper.eq("job_name", dto.getJobName());
        } else if (StringUtils.isNotBlank(dto.getGroupName()) && StringUtils.isBlank(dto.getJobName())) {
            queryWrapper.eq("group_name", dto.getGroupName());
        }
        return new PageInfo<>(jobManagerMapper.selectList(queryWrapper));
    }
}
