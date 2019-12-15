package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.PermissionMapper;
import com.wugui.dataxweb.dao.RoleMapper;
import com.wugui.dataxweb.dao.RolePermissionMapper;
import com.wugui.dataxweb.dao.UserRoleMapper;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.entity.RolePermission;
import com.wugui.dataxweb.entity.UserRole;
import com.wugui.dataxweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private RoleMapper roleMapper;

    private UserRoleMapper userRoleMapper;

    private RolePermissionMapper rolePermissionMapper;

    private PermissionMapper permissionMapper;


    @Override
    public List<Role> getAllByUserId(Long userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(userRoles)) {
            List<Long> roleIds = userRoles.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.in("id", roleIds);
            List<Role> roleList = roleMapper.selectList(roleQueryWrapper);
            if (!CollectionUtils.isEmpty(roleList)) {
                QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
                QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
                for (Role role : roleList) {
                    rolePermissionQueryWrapper.eq("role_id", role.getId());
                    List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);
                    if (!CollectionUtils.isEmpty(rolePermissions)) {
                        role.setPermissionList(permissionMapper.selectList(permissionQueryWrapper.in("id", rolePermissions.stream().map(t->t.getPermissionId()).collect(Collectors.toList()))));
                    }
                }
                return roleList;
            }
            return null;
        }
        return null;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
