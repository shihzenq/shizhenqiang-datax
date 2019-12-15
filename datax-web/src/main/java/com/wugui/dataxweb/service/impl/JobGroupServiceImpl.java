package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.GroupMapper;
import com.wugui.dataxweb.dto.group.GroupSearchDTO;
import com.wugui.dataxweb.dto.group.GroupUpdateDTO;
import com.wugui.dataxweb.entity.JobGroupEntity;
import com.wugui.dataxweb.service.JobGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobGroupServiceImpl extends ServiceImpl<GroupMapper, JobGroupEntity> implements JobGroupService {

    private GroupMapper groupMapper;

    @Override
    public JobGroupEntity add(JobGroupEntity groupEntity) {
        groupMapper.insert(groupEntity);
        return groupEntity;
    }

    @Override
    public Boolean countGroupName(String name, Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("createUserId", userId);
        List<JobGroupEntity> jobGroupEntities = groupMapper.selectByMap(map);
        return CollectionUtils.isEmpty(jobGroupEntities);
    }

    @Override
    public PageInfo<JobGroupEntity> getAll(GroupSearchDTO dto, Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", dto.getName());
        map.put("createUserId", userId);
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(groupMapper.selectByMap(map));
    }

    @Override
    public Boolean deleteById(Long id) {
        return groupMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean update(GroupUpdateDTO dto) {
        JobGroupEntity jobGroupEntity = new JobGroupEntity();
        BeanUtils.copyProperties(dto, jobGroupEntity);
        return groupMapper.updateById(jobGroupEntity) > 0;
    }

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }
}
