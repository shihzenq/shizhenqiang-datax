package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.PermissionMapper;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAll() {
        return null;
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
    public Boolean validatePermissionCodeExist(UserEntity user, String code) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        wrapper.eq("code", code);
        return permissionMapper.selectOne(wrapper) != null;
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
}
