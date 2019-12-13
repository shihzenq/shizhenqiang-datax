package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.GroupMapper;
import com.wugui.dataxweb.entity.JobGroupEntity;
import com.wugui.dataxweb.service.JobGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobGroupServiceImpl extends ServiceImpl<GroupMapper, JobGroupEntity> implements JobGroupService {

    private GroupMapper groupMapper;

    @Override
    public JobGroupEntity add(JobGroupEntity groupEntity) {
        groupMapper.insert(groupEntity);
        return groupEntity;
    }

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }
}
