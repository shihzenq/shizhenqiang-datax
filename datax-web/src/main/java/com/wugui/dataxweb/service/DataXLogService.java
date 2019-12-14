package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.DataXLog;

public interface DataXLogService extends IService<DataXLog> {
    DataXLog add(DataXLog dataXLog);
}
