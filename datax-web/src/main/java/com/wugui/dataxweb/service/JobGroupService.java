package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.JobGroupEntity;

public interface JobGroupService extends IService<JobGroupEntity> {

    JobGroupEntity add(JobGroupEntity groupEntity);

}
