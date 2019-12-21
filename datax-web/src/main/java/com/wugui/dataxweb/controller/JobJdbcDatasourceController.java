package com.wugui.dataxweb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.config.Constants;
import com.wugui.dataxweb.config.security.RequiredPermission;
import com.wugui.dataxweb.constants.Permissions;
import com.wugui.dataxweb.dto.SearchDTO;
import com.wugui.dataxweb.dto.datasource.CreatTableDTO;
import com.wugui.dataxweb.dto.datasource.CreatTableSyncDTO;
import com.wugui.dataxweb.dto.datasource.DataSourceIdDTO;
import com.wugui.dataxweb.dto.datasource.DataSourceIdListDTO;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import com.wugui.dataxweb.log.OperateLog;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import com.wugui.dataxweb.util.DriverUtils;
import com.wugui.dataxweb.util.IpUtils;
import com.wugui.dataxweb.util.PageUtils;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * jdbc数据源配置控制器层
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/api/jobJdbcDatasource")
@Api(tags = "系统管理模块-数据源管理页面")
public class JobJdbcDatasourceController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IJobJdbcDatasourceService jobJdbcDatasourceService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @ApiOperation("分页查询所有数据，系统管理模块-数据源管理页面-数据源分页查询接口")
    @OperateLog(content = "数据源查询")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "current", value = "当前页", defaultValue = "1", required = true),
                    @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "size", value = "一页大小", defaultValue = "10", required = true)
            })
    public ResponseData<PageInfo<JobJdbcDatasource>> selectAll(Integer current, Integer size) {
//        BaseForm<JobJdbcDatasource> baseForm = new BaseForm();
//        Long userId = getCurrentUser().getId();
//        IPage<JobJdbcDatasource> page = this.jobJdbcDatasourceService.page(baseForm.getPlusPagingQueryEntity(), pageQueryWrapperCustom(baseForm.getParameters()));
//        List<JobJdbcDatasource> records = page.getRecords();
//        List<JobJdbcDatasource> list = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(records)) {
//            records.forEach(t-> {
//                if (userId.equals(t.getUserId())) {
//                    list.add(t);
//                }
//            });
//        }
//        if (!CollectionUtils.isEmpty(list)) {
//            return response()
//        }
//        return response();
        Long userId = getCurrentUser().getId();
        return response(jobJdbcDatasourceService.selectAll(userId, current, size));
    }

    /**
     * 自定义查询组装
     *
     * @param map
     * @return
     */
    protected QueryWrapper<JobJdbcDatasource> pageQueryWrapperCustom(Map<String, Object> map) {
        // mybatis plus 分页相关的参数
        Map<String, Object> pageHelperParams = PageUtils.filterPageParams(map);
        logger.info("分页相关的参数: {}", pageHelperParams);
        //过滤空值，分页查询相关的参数
        Map<String, Object> columnQueryMap = PageUtils.filterColumnQueryParams(map);
        logger.info("字段查询条件参数为: {}", columnQueryMap);

        QueryWrapper<JobJdbcDatasource> queryWrapper = new QueryWrapper<>();

        //排序 操作
        pageHelperParams.forEach((k, v) -> {
            switch (k) {
                case "ascs":
                    queryWrapper.orderByAsc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
                    break;
                case "descs":
                    queryWrapper.orderByDesc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
                    break;
            }
        });

        //遍历进行字段查询条件组装
        columnQueryMap.forEach((k, v) -> {
            switch (k) {
                case "datasourceName":
                    queryWrapper.like(StrUtil.toUnderlineCase(k), v);
                    break;
                default:
                    queryWrapper.eq(StrUtil.toUnderlineCase(k), v);
            }
        });

        return queryWrapper;
    }


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @ApiOperation("jdbc数据源详情，系统管理模块-数据源管理页面-数据源详情接口")
    @PostMapping("/id")
    @OperateLog(content = "数据源详情")
    public ResponseData<?> selectOne(@RequestBody DataSourceIdDTO dto) {
        return response(this.jobJdbcDatasourceService.getById(dto.getId()));
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增jdbc数据源，系统管理模块-数据源管理页面-数据源新增接口")
    @PostMapping("/add")
    @OperateLog(content = "数据源新增")
    public ResponseData<?> insert(@RequestBody JobJdbcDatasource entity) {
        entity.setUserId(getCurrentUser().getId());
//        String ipAddress = IpUtils.getIpAddress(request);
//        entity.setIpAddress(ipAddress);
        String type = DriverUtils.driverMap.get(entity.getType().toUpperCase());
        entity.setJdbcDriverClass(type);
        // jdbc:mysql://127.0.0.1:3306/wkxz_3_dev
        entity.setJdbcUrl("jdbc:"+entity.getType()+"://"+ entity.getIpAddress()+":"+ entity.getPort() +"/"+entity.getDatasourceName());
        return response(this.jobJdbcDatasourceService.save(entity));
    }

    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @RequiredPermission(value = Permissions.DATA_SOURCE_DETAIL)
    @PostMapping("/update")
    @ApiOperation("修改jdbc数据源，系统管理模块-数据源管理页面-数据源修改接口")
    @OperateLog(content = "数据源修改")
    public ResponseData<?> update(@RequestBody JobJdbcDatasource entity) {
        String type = DriverUtils.driverMap.get(entity.getType().toUpperCase());
        entity.setJdbcDriverClass(type);
        // jdbc:mysql://127.0.0.1:3306/wkxz_3_dev
        entity.setJdbcUrl("jdbc:"+entity.getType()+"://"+ entity.getIpAddress()+":"+ entity.getPort() +"/"+entity.getJdbcUsername());
        return response(this.jobJdbcDatasourceService.updateById(entity));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @RequiredPermission(value = Permissions.DATA_SOURCE_delete)
    @PostMapping("/delete")
    @ApiOperation("删除jdbc数据源，系统管理模块-数据源管理页面-数据源删除接口")
    @OperateLog(content = "数据源删除")
    public ResponseData<?> delete(@RequestBody DataSourceIdDTO dto) {
        return response(this.jobJdbcDatasourceService.removeById(dto.getId()));
    }


    @PostMapping("/create-table")
    @ApiOperation("创建表，用户选定要创建的字段")
    @OperateLog(content = "目标数据库创建表")
    public ResponseData<?> createTable(@RequestBody @Validated CreatTableDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseFormError(bindingResult);
        }
        JobJdbcDatasource jobJdbcDatasource = jobJdbcDatasourceService.getById(dto.getId());
        if (null == jobJdbcDatasource) {
            return responseError("无此数据源！");
        }
        return response(jobJdbcDatasourceService.createTable(jobJdbcDatasource, dto));
    }
}