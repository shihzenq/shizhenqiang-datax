package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
