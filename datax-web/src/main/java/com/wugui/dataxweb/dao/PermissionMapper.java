package com.wugui.dataxweb.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {

    List<String> selectPermissionsByUserId(Long id);
}
