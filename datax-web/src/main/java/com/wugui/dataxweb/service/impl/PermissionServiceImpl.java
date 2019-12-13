package com.wugui.dataxweb.service.impl;

import com.wugui.dataxweb.dao.PermissionMapper;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAll() {
        return null;
    }

    @Override
    public List<String> getPermissionsByUser(UserEntity user) {
        List<String> permissions = permissionMapper.selectPermissionsByUserId(user.getId());
//        if (accountSet != null && GeneralStatusEnum.Disable.value().equals(accountSet.getStatus())) {
//            permissions.removeAll(DISABLED_ACCOUNT_SET_NO_PERMISSIONS);
//        }
        return permissions;
    }

    @Override
    public Boolean validatePermissionCodeExist(UserEntity user, String code) {
        return null;
    }

    @Override
    public Permission permissionManagerAdd(Permission permission) {
        return null;
    }

    @Override
    public int countByCode(String code) {
        return 0;
    }

    @Override
    public Boolean update() {
        return null;
    }

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
