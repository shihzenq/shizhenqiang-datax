package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.PermissionMapper;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import com.wugui.dataxweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private PermissionMapper permissionMapper;

    private RoleService roleService;

    @Override
    public List<Permission> getAll() {
        return permissionMapper.selectList(null);
    }

    @Override
    public List<String> getPermissionsByUser(UserEntity user) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        List<Permission> permissions = permissionMapper.selectList(wrapper);
//        if (accountSet != null && GeneralStatusEnum.Disable.value().equals(accountSet.getStatus())) {
//            permissions.removeAll(DISABLED_ACCOUNT_SET_NO_PERMISSIONS);
//        }

        if (!CollectionUtils.isEmpty(permissions)) {
            return permissions.stream().map(t-> t.getCode()).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean validatePermissionPathExist(UserEntity user, String path) {
        List<Role> roleList = roleService.getAllByUserId(user.getId());
        if (CollectionUtils.isEmpty(roleList)) return false;
        for (Role role : roleList) {
            List<Permission> permissionList = role.getPermissionList();
            for (Permission permission : permissionList) {
                if (permission.getPath().equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Permission permissionManagerAdd(Permission permission) {
        permissionMapper.insert(permission);
        return permission;
    }

    @Override
    public int countByCode(String code) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        return permissionMapper.selectCount(wrapper);
    }

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
