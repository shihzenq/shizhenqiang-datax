package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    Permission selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Permission permission);

    int updateByPrimaryKeySelective(Permission permission);

    List<String> selectPermissionsByUserId(Long id);

    List<Permission> selectPermissionByUserIdAndCode(@Param("userId")Long userId, @Param("code")String code);

    List<Permission> getAll();

    List<Permission> getPermissionsByRole(@Param("roleId") Long roleId);
}
