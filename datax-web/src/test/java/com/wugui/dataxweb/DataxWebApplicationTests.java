package com.wugui.dataxweb;

import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.JobConfigMapper;
import com.wugui.dataxweb.entity.JobConfig;
import com.wugui.dataxweb.entity.Permission;
import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.PermissionService;
import com.wugui.dataxweb.service.RoleService;
import com.wugui.dataxweb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataxWebApplicationTests {

    @Autowired
    JobConfigMapper jobConfigMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Test
    public void contextLoads() {
        JobConfig jobConfig = new JobConfig();
        jobConfig.setConfigJson("{}");
        jobConfigMapper.insert(jobConfig);
    }


    @Test
    public void addUser() {
        UserEntity entity = new UserEntity();
        entity.setPhone("15652182014");
        entity.setUsername("时贞强");
        entity.setSex(1);
        entity.setPassword("123456");
        entity.setAdmin(true);
        entity.setCreateUserId(0L);
        userService.add(entity);
        System.out.println(entity);
    }

    @Test
    public void updateUser() {
        UserEntity entity = new UserEntity();
        entity.setId(379L);
        entity.setPhone("15652182014");
        entity.setUsername("时贞强update");
        entity.setSex(1);
        entity.setPassword("123456");
        entity.setAdmin(true);
        entity.setCreateUserId(0L);
        userService.updateUser(entity);
        System.out.println(entity);
    }


    @Test
    public void listUser() {
        PageInfo<UserEntity> all = userService.getAll(null, 1, 10);
        System.out.println(all);
    }


    @Test
    public void listPermission() {
        List<Permission> all = permissionService.getAll();
        System.out.println(all);
    }

    @Test
    public void listRole() {
        List<Role> allByUserId = roleService.getAllByUserId(378L);
        System.out.println(allByUserId);
    }



}
