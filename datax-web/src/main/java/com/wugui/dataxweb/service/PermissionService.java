package com.wugui.dataxweb.service;

import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.UserEntity;

import java.util.List;

public interface PermissionService {

    List<Permission> getAll();

    /**
     * 获取企业用户的所有权限
     * @param user 用户对象
     * @return 权限code列表
     */
    List<String> getPermissionsByUser(UserEntity user);

    /**
     * 判断用户是否存在某一项权限
     *
     * @param user       用户对象
     * @param code       权限code
     * @return Boolean
     */
    Boolean validatePermissionCodeExist(UserEntity user, String code);

    /**
     * 新增权限节点
     * @param permission 权限
     * @return Permission
     */
    Permission permissionManagerAdd(Permission permission);

    /**
     * 校验权限编码
     * @param code 权限编码
     * @return int
     */
    int countByCode(String code);

    /**
     * 修改权限
     * @return Boolean
     */
    Boolean update();
}
