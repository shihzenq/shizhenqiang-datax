package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dto.job.JobManagerDTO;
import com.wugui.dataxweb.entity.JobManagerEntity;

import java.util.List;

public interface JobManagerService extends IService<JobManagerEntity> {

    PageInfo<JobManagerEntity> list(JobManagerDTO dto);
}
