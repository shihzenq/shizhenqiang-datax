package com.wugui.dataxweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugui.dataxweb.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserEntity>{

    UserEntity getByUsername(@Param("username") String username);

    int countByUsername(@Param("username") String username);

    int insertSelectiveUser(UserEntity userEntity);

    List<UserEntity> getAll(@Param("username") String username);

    int updateByPrimaryKeySelectiveUser(UserEntity userEntity);

    //int deleteUpdate(Long id);
}
