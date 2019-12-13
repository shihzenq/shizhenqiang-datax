package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.JobDataSourceEntity;

import java.util.List;

public interface JobDataSourceService extends IService<JobDataSourceEntity> {

    JobDataSourceEntity add(JobDataSourceEntity jobDataSourceEntity);

    boolean update(JobDataSourceEntity jobDataSourceEntity);

    List<JobDataSourceEntity>  selectAll(Long userId);
}
