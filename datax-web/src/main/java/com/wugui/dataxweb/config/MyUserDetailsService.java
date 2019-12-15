package com.wugui.dataxweb.config;

import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.RoleService;
import com.wugui.dataxweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: MyUserDetailsService
 * @Description:
 * @Author GX
 * @Date 2018/06/11 上午 11:49
 * @Version V1.0
 */
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 根据用户名获取登录用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("用户名："+ username + "不存在！");
        }
        List<Role> roleList = roleService.getAllByUserId(user.getId());
        if (!CollectionUtils.isEmpty(roleList)) {
            Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
            Iterator<Role> iterator =  roleList.iterator();
            while (iterator.hasNext()){
                collection.add(new SimpleGrantedAuthority(iterator.next().getName()));
            }
            return new org.springframework.security.core.userdetails.User(username,user.getPassword(),collection);
        }
        return null;
    }

}
