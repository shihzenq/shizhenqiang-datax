package com.wugui.dataxweb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dto.job.JobManagerConfigDTO;
import com.wugui.dataxweb.dto.job.JobManagerDTO;
import com.wugui.dataxweb.dto.job.JobManagerIdDTO;
import com.wugui.dataxweb.entity.JobConfig;
import com.wugui.dataxweb.entity.JobGroupEntity;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import com.wugui.dataxweb.entity.JobManagerEntity;
import com.wugui.dataxweb.service.IJobConfigService;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import com.wugui.dataxweb.service.JobManagerService;
import com.wugui.dataxweb.util.PageUtils;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 作业配置表控制器层
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/api/jobConfig")
@Api(tags = "通用信息->输入配置->输出配置、作业配置页面的接口")
public class JobConfigController extends BaseController {

    @Autowired
    private JobManagerService jobManagerService;
    @Autowired
    private IJobJdbcDatasourceService iJobJdbcDatasourceService;
//    /**
//     * 服务对象
//     */
//    @Autowired
//    private IJobConfigService jobConfigService;
//
//    /**
//     * 分页查询所有数据
//     *
//     * @return 所有数据
//     */
//    @GetMapping
//    @ApiOperation("分页查询所有数据")
//    @ApiImplicitParams(
//            {@ApiImplicitParam(paramType = "query", dataType = "String", name = "current", value = "当前页", defaultValue = "1", required = true),
//                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "size", value = "一页大小", defaultValue = "10", required = true),
//                    @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "ifCount", value = "是否查询总数", defaultValue = "true"),
//                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "ascs", value = "升序字段，多个用逗号分隔"),
//                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "descs", value = "降序字段，多个用逗号分隔")
//            })
//    public R<IPage<JobConfig>> selectAll() {
//        BaseForm<JobConfig> baseForm = new BaseForm();
//        return success(this.jobConfigService.page(baseForm.getPlusPagingQueryEntity(), pageQueryWrapperCustom(baseForm.getParameters())));
//    }
//
//    /**
//     * 自定义查询组装
//     *
//     * @param map
//     * @return
//     */
//    protected QueryWrapper<JobConfig> pageQueryWrapperCustom(Map<String, Object> map) {
//        // mybatis plus 分页相关的参数
//        Map<String, Object> pageHelperParams = PageUtils.filterPageParams(map);
//        logger.info("分页相关的参数: {}", pageHelperParams);
//        //过滤空值，分页查询相关的参数
//        Map<String, Object> columnQueryMap = PageUtils.filterColumnQueryParams(map);
//        logger.info("字段查询条件参数为: {}", columnQueryMap);
//
//        QueryWrapper<JobConfig> queryWrapper = new QueryWrapper<>();
//
//        //排序 操作
//        pageHelperParams.forEach((k, v) -> {
//            switch (k) {
//                case "ascs":
//                    queryWrapper.orderByAsc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
//                    break;
//                case "descs":
//                    queryWrapper.orderByDesc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
//                    break;
//            }
//        });
//
//        //遍历进行字段查询条件组装
//        columnQueryMap.forEach((k, v) -> {
//            switch (k) {
//                case "name":
//                    queryWrapper.like("name", v);
//                    break;
//                case "jobGroup":
//                    queryWrapper.like(StrUtil.toUnderlineCase("jobGroup"), v);
//                    break;
//                default:
//                    queryWrapper.eq(StrUtil.toUnderlineCase(k), v);
//            }
//        });
//
//        return queryWrapper;
//    }
//
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @ApiOperation("通过主键查询单条数据")
//    @GetMapping("{id}")
//    public R<JobConfig> selectOne(@PathVariable Serializable id) {
//        return success(this.jobConfigService.getById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param entity 实体对象
//     * @return 新增结果
//     */
//    @ApiOperation("新增数据")
//    @PostMapping
//    public R<Boolean> insert(@RequestBody JobConfig entity) {
//        return success(this.jobConfigService.save(entity));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param entity 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    @ApiOperation("修改数据")
//    public R<Boolean> update(@RequestBody JobConfig entity) {
//        return success(this.jobConfigService.updateById(entity));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param idList 主键结合
//     * @return 删除结果
//     */
//    @DeleteMapping
//    @ApiOperation("删除数据")
//    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
//        return success(this.jobConfigService.removeByIds(idList));
//    }

    @PostMapping("/list")
    @ApiOperation("作业管理页面分页查询")
    public ResponseData<PageInfo<JobManagerEntity>> list(@RequestBody JobManagerDTO dto) {
        return response(jobManagerService.list(dto));
    }

    @PostMapping("/add")
    @ApiOperation("作业管理页面添加数据")
    public ResponseData<?> add(@RequestBody JobManagerEntity dto) {
        QueryWrapper<JobManagerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_name", dto.getJobName());
        queryWrapper.eq("group_name", dto.getGroupName());
        JobManagerEntity jobManagerEntity = jobManagerService.getOne(queryWrapper);
        if (null!=jobManagerEntity) {
            return responseError("作业管理添加重复");
        }
        jobManagerEntity = new JobManagerEntity();
        BeanUtils.copyProperties(dto, jobManagerEntity);
        jobManagerEntity.setCreateUserId(getCurrentUser().getId());
        jobManagerEntity.setCreateUserName(getCurrentUser().getUsername());
        if (null != jobManagerEntity.getSourceId()) {
            JobJdbcDatasource jobJdbcDatasource = iJobJdbcDatasourceService.getById(jobManagerEntity.getSourceId());
            jobManagerEntity.setSourceIp(jobJdbcDatasource.getIpAddress());
        }
        if (null != jobManagerEntity.getTargetId()) {
            JobJdbcDatasource jobJdbcDatasource = iJobJdbcDatasourceService.getById(jobManagerEntity.getTargetId());
            jobManagerEntity.setTargetIp(jobJdbcDatasource.getIpAddress());
        }
        return response(jobManagerService.save(jobManagerEntity));
    }


    @PostMapping("/delete")
    @ApiOperation("作业管理页面删除数据")
    public ResponseData<?> delete(@RequestBody JobManagerIdDTO dto) {
        return response(jobManagerService.removeById(dto.getId()));
    }

    @PostMapping("/update")
    @ApiOperation("作业管理页面-JSON配置作业页面")
    public ResponseData<?> delete(@RequestBody @Validated JobManagerConfigDTO dto) {
        QueryWrapper<JobManagerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_name", dto.getJobName());
        queryWrapper.eq("group_name", dto.getGroupName());
        JobManagerEntity jobManagerEntity = jobManagerService.getOne(queryWrapper);
        if (null != jobManagerEntity) {
            jobManagerEntity.setJobJson(dto.getJobJson());
        }
        return response(jobManagerService.updateById(jobManagerEntity));
    }

}