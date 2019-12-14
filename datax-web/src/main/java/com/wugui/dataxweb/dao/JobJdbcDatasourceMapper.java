package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * jdbc数据源配置表数据库访问层
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-07-30
 */
@Repository
public interface JobJdbcDatasourceMapper extends BaseMapper<JobJdbcDatasource> {


    List<JobJdbcDatasource> selectAll(@Param("userId") Long userId);
}