package com.wugui.dataxweb.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.wugui.dataxweb.dto.datasource.DataSourceDTO;
import com.wugui.dataxweb.dto.group.GroupDTO;
import com.wugui.dataxweb.entity.JobDataSourceEntity;
import com.wugui.dataxweb.entity.JobGroupEntity;
import com.wugui.dataxweb.service.JobGroupService;
import com.wugui.dataxweb.util.BindingResultUtils;
import com.wugui.dataxweb.validator.group.AddChecks;
import com.wugui.dataxweb.validator.group.UpdateChecks;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-manage")
@Api("系统管理")
public class SystemManagementController extends ApiController {


    private JobGroupService JobGroupService;

    @ApiOperation(value = "作业组管理新增" , notes = "作业组管理新增")
    @PostMapping("/add-group")
    public R<?> addGroup(@RequestBody @Validated({AddChecks.class}) GroupDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.failed(BindingResultUtils.getErrorMessage(bindingResult));
        }
        JobGroupEntity groupEntity = new JobGroupEntity();
        BeanUtils.copyProperties(dto, groupEntity);
        JobGroupEntity entity = JobGroupService.add(groupEntity);
        return R.ok(entity);
    }

    @Autowired
    public void setJobGroupService(com.wugui.dataxweb.service.JobGroupService jobGroupService) {
        JobGroupService = jobGroupService;
    }

    @Autowired


    @PostMapping("/add-datasource")
    @ApiOperation(value = "数据源新建连接")
    public R<?> addDatasource(@RequestBody @Validated({UpdateChecks.class}) DataSourceDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.failed(BindingResultUtils.getErrorMessage(bindingResult));
        }
        JobDataSourceEntity dataSourceEntity = new JobDataSourceEntity();
        BeanUtils.copyProperties(dto, dataSourceEntity);
        return null;
    }
}
