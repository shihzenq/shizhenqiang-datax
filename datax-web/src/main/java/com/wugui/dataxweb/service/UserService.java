package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    UserEntity getByUsername(String username);

    UserEntity get(Long id);
}
