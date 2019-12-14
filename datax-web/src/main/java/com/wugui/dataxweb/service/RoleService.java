package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> getAllByUserId(Long userId);

}
