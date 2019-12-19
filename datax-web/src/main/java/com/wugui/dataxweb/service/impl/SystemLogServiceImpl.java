package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.SystemLogMapper;
import com.wugui.dataxweb.entity.SystemLogEntity;
import com.wugui.dataxweb.service.SystemLogService;
import org.springframework.stereotype.Service;

@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogEntity> implements SystemLogService {
}
