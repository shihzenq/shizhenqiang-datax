package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dto.group.GroupSearchDTO;
import com.wugui.dataxweb.dto.group.GroupUpdateDTO;
import com.wugui.dataxweb.entity.JobGroupEntity;

public interface JobGroupService extends IService<JobGroupEntity> {

    JobGroupEntity add(JobGroupEntity groupEntity);

    Boolean countGroupName(String name, Long userId);

    PageInfo<JobGroupEntity> getAll(GroupSearchDTO dto, Long userId);

    Boolean deleteById(Long id);

    Boolean update(GroupUpdateDTO dto);
}
