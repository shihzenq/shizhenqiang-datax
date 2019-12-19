package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.SystemLogMapper;
import com.wugui.dataxweb.entity.SystemLogEntity;
import com.wugui.dataxweb.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogEntity> implements SystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public PageInfo<SystemLogEntity> list(Long id, Integer pageNum, Integer pageSize) {
        QueryWrapper<SystemLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(systemLogMapper.selectList(queryWrapper));
    }
}
