package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dto.datasource.CreatTableDTO;
import com.wugui.dataxweb.dto.datasource.CreatTableSyncDTO;
import com.wugui.dataxweb.entity.JobJdbcDatasource;

/**
 * jdbc数据源配置表服务接口
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-07-30
 */
public interface IJobJdbcDatasourceService extends IService<JobJdbcDatasource> {

    PageInfo<JobJdbcDatasource> selectAll(Long userId, Integer pageNum, Integer pageSize);

    Boolean createTable(JobJdbcDatasource jdbcDatasource, CreatTableDTO dto);

    Boolean createTableSync(CreatTableSyncDTO dto);
}