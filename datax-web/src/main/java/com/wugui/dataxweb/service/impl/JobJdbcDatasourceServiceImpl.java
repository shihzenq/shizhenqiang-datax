package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.JobJdbcDatasourceMapper;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * jdbc数据源配置表服务实现类
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-07-30
 */
@Service
@Transactional(readOnly = true)
public class JobJdbcDatasourceServiceImpl extends ServiceImpl<JobJdbcDatasourceMapper, JobJdbcDatasource> implements IJobJdbcDatasourceService {

    private JobJdbcDatasourceMapper jobJdbcDatasourceMapper;

    @Override
    public PageInfo<JobJdbcDatasource> selectAll(Long userId, Integer pageNum, Integer pageSize) {
        if (null != pageNum && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);

        } else {
            PageHelper.startPage(1, 10);
        }
        QueryWrapper<JobJdbcDatasource> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return new PageInfo<>(jobJdbcDatasourceMapper.selectList(wrapper));
    }

    @Autowired
    public void setJobJdbcDatasourceMapper(JobJdbcDatasourceMapper jobJdbcDatasourceMapper) {
        this.jobJdbcDatasourceMapper = jobJdbcDatasourceMapper;
    }
}