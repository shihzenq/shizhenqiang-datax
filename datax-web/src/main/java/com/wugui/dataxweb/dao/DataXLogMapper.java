package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.DataXLog;
import com.wugui.dataxweb.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DataXLogMapper extends BaseMapper<DataXLog> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(DataXLog record);

    DataXLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataXLog record);

}