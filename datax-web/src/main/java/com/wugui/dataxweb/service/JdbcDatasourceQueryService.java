package com.wugui.dataxweb.service;

import com.wugui.dataxweb.vo.DataSourceColumnVO;
import com.wugui.dataxweb.vo.DataSourceTablesVO;
import com.wugui.tool.database.TableInfo;

import java.util.List;

/**
 * 数据库查询服务层
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName JdbcDatasourceQueryService
 * @Version 1.0
 * @since 2019/7/31 20:50
 */
public interface JdbcDatasourceQueryService {

    /**
     * 根据数据源表id查询出可用的表
     *
     * @param id
     * @return
     */
    DataSourceTablesVO getTables(Long id);


    /**
     * 根据数据源id，表名查询出该表所有字段
     *
     * @param id
     * @return
     */
    DataSourceColumnVO getColumns(Long id, String tableName);

    /**
     * 根据 sql 语句获取字段
     *
     * @param datasourceId
     * @param querySql
     * @return
     */
    List<String> getColumnsByQuerySql(Long datasourceId, String querySql);


    TableInfo getTableAndColumnsDetails(Long datasourceId, String tableName);
}
