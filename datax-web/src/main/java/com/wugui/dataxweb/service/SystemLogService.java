package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.entity.SystemLogEntity;

public interface SystemLogService extends IService<SystemLogEntity> {

    PageInfo<SystemLogEntity> list(Long id, Integer pageNum, Integer pageSize);
}
