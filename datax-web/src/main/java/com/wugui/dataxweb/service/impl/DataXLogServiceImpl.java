package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.DataXLogMapper;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.service.DataXLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataXLogServiceImpl extends ServiceImpl<DataXLogMapper, DataXLog> implements DataXLogService {

    private DataXLogMapper dataXLogMapper;

    @Override
    public DataXLog add(DataXLog dataXLog) {
        dataXLogMapper.insert(dataXLog);
        return dataXLog;
    }

    @Override
    public Boolean delete(Long id) {
        return dataXLogMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<DataXLog> list(Long userId, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("creat_user_id", userId);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(dataXLogMapper.selectByMap(map));
    }

    @Autowired
    public void setDataXLogMapper(DataXLogMapper dataXLogMapper) {
        this.dataXLogMapper = dataXLogMapper;
    }
}
