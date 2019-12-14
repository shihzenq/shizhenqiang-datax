package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /**
     * Created by luojiayi on 2018/7/2 9:53
     * Description:根据企业ID查询企业下所有岗位（角色）
     */
    List<Role> getRoleByEnterpriseId(@Param("enterpriseId") Long enterpriseId);

    /**
     * Created by luojiayi on 2018/7/3 13:34
     * Description:根据企业id和岗位（角色）名称查询
     */
    List<Role> getRoleByEnterpriseIdAndName(Role role);

    /**
     * Created by luojiayi on 2018/7/3 13:34
     * Description:根据企业id和岗位（角色）名称查询
     */
    int getRoleByEnterpriseIdAndNameCount(Role role);

    /**
     * Created by luojiayi on 2018/7/3 17:59
     * Description:查询岗位（角色）下是否有用户
     */
    List<Long> getUserIdByRoleId(@Param("id") Long id);

    /**
     * 根据用户id查询角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> selectRoleListByUserId(Long userId);

    /**
     * 同步预置角色信息
     *
     * @param enterpriseId 企业id
     * @param createUserId 创建用户id
     * @param createTime   创建时间
     * @return 同步成功
     */
    int insertFromPreRole(@Param("enterpriseId") Long enterpriseId,
                          @Param("createUserId") Long createUserId, @Param("createTime") Date createTime);

    int insertRolePermissionFromPrePermission(Long enterpriseId);
}