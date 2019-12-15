package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.RoleMapper;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllByUserId(Long userId) {
//        QueryWrapper<Role> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id", userId);
//        wrapper.eq("code", code);
        return roleMapper.selectRoleListByUserId(userId);
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
}
