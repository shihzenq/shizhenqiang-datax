package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.entity.DataXLog;

import java.util.List;

public interface DataXLogService extends IService<DataXLog> {
    DataXLog add(DataXLog dataXLog);

    Boolean delete(Long id);

    PageInfo<DataXLog> list(Long userId, Integer pageNum, Integer pageSize);
}
