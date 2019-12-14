package com.wugui.dataxweb.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.vo.ResponseData;

import java.util.List;

public interface UserService extends IService<UserEntity> {
    UserEntity getByUsername(String username);

    UserEntity get(Long id);

    JSONObject importUser(byte[] bytes, UserEntity enterpriseUser);

    List<UserEntity> getAll();

    Boolean updatePassword(UserEntity userEntity, String newPassword);

    Boolean updateUser(UserEntity userEntity);

    Boolean deleteUpdate(Long id);
}
