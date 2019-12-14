package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.DataXLogMapper;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.service.DataXLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataXLogServiceImpl extends ServiceImpl<DataXLogMapper, DataXLog> implements DataXLogService {

    private DataXLogMapper dataXLogMapper;

    @Override
    public DataXLog add(DataXLog dataXLog) {
        dataXLogMapper.insertSelective(dataXLog);
        return dataXLog;
    }

    @Autowired
    public void setDataXLogMapper(DataXLogMapper dataXLogMapper) {
        this.dataXLogMapper = dataXLogMapper;
    }
}
