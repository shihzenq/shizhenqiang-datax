package com.wugui.dataxweb.service.impl;

import com.alibaba.druid.util.JdbcUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.JobJdbcDatasourceMapper;
import com.wugui.dataxweb.dto.datasource.CreatTableDTO;
import com.wugui.dataxweb.entity.JobJdbcDatasource;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import com.wugui.dataxweb.util.JDBCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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

    @Override
    public Boolean createTable(JobJdbcDatasource jdbcDatasource, CreatTableDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {

            conn= JDBCUtils.getConn(jdbcDatasource.getJdbcUrl(), jdbcDatasource.getJdbcUsername(), jdbcDatasource.getJdbcPassword());
            String sql = JDBCUtils.createTable(dto.getTableName(), dto.getColunmList());
            pstmt = JDBCUtils.getPStmt(conn, sql);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JDBCUtils.closeStmt(pstmt);
            JDBCUtils.closeConn(conn);
        }
        return true;
    }

    @Autowired
    public void setJobJdbcDatasourceMapper(JobJdbcDatasourceMapper jobJdbcDatasourceMapper) {
        this.jobJdbcDatasourceMapper = jobJdbcDatasourceMapper;
    }
}