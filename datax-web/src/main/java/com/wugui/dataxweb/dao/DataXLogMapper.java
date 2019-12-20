package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.DataXLog;
import org.springframework.stereotype.Repository;

@Repository
public interface DataXLogMapper extends BaseMapper<DataXLog> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(DataXLog record);

    DataXLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataXLog record);

}