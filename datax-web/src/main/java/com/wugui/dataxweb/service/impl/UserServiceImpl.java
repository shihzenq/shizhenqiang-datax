package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wugui.dataxweb.dao.UserMapper;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public UserEntity getByUsername(String username) {
        return null;
    }

    @Override
    public UserEntity get(Long id) {
        return null;
    }
}
