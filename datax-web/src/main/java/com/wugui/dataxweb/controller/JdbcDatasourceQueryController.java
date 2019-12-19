package com.wugui.dataxweb.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.wugui.dataxweb.dto.datasource.CreatTableSyncDTO;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import com.wugui.dataxweb.log.OperateLog;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import com.wugui.dataxweb.service.JdbcDatasourceQueryService;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询数据库表名，字段的控制器
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName DatabaseQueryController
 * @Version 1.0
 * @since 2019/7/31 20:48
 */
@RestController
@RequestMapping("api/jdbcDatasourceQuery")
@Api(tags = "具体接口是：作业管理模块-输入配置页面的接口，页面上的修改表名、字段名、模糊查询暂时不做")
public class JdbcDatasourceQueryController extends BaseController {

    @Autowired
    private JdbcDatasourceQueryService jdbcDatasourceQueryService;

    @Autowired
    private IJobJdbcDatasourceService jobJdbcDatasourceService;

    /**
     * 根据数据源id获取可用表名
     *
     * @param datasourceId
     * @return
     */
    @GetMapping("/getTables")
    @ApiOperation("根据数据源id获取可用表名，数据源就是在系统管理模块下的数据源管理添加的数据")
    @OperateLog(content = "获取可用表名")
    public ResponseData<List<String>> getTableNames(Long datasourceId) {
        return response(jdbcDatasourceQueryService.getTables(datasourceId));
    }

    /**
     * 根据数据源id和表名获取所有字段
     *
     * @param datasourceId 数据源id
     * @param tableName    表名
     * @return
     */
    @GetMapping("/getColumns")
    @ApiOperation("根据数据源id和表名获取所有字段，数据源就是在系统管理模块下的数据源管理添加的数据")
    @OperateLog(content = "获取表下所有字段")
    public ResponseData<List<String>> getColumns(Long datasourceId, String tableName) {
        return response(jdbcDatasourceQueryService.getColumns(datasourceId, tableName));
    }

    /**
     * 根据数据源id和sql语句获取所有字段
     *
     * @param datasourceId 数据源id
     * @param querySql     表名
     * @return
     */
    @GetMapping("/getColumnsByQuerySql")
    @ApiOperation("根据数据源id和sql语句获取所有字段，数据源就是在系统管理模块下的数据源管理添加的数据")
    @OperateLog(content = "获取表下所有字段")
    public ResponseData<List<String>> getColumnsByQuerySql(Long datasourceId, String querySql) {
        return response(jdbcDatasourceQueryService.getColumnsByQuerySql(datasourceId, querySql));
    }

    @PostMapping("/create-table-sync")
    @ApiOperation("创建表，将源数据库表及字段信息同步创建目标源数据库表中")
    @OperateLog(content = "源数据库表及字段信息同步创建目标源数据库表中")
    public ResponseData<?> createTableSync(@RequestBody @Validated CreatTableSyncDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseFormError(bindingResult);
        }
        JobJdbcDatasource sourceJdbcDatasource = jobJdbcDatasourceService.getById(dto.getSourceId());
        if (null == sourceJdbcDatasource) {
            return responseError("无此源数据源！");
        }
        JobJdbcDatasource targetJdbcDatasource = jobJdbcDatasourceService.getById(dto.getTargetId());
        if (null == targetJdbcDatasource) {
            return responseError("无此目标数据源！");
        }
        return response(jobJdbcDatasourceService.createTableSync(dto));
    }

}
