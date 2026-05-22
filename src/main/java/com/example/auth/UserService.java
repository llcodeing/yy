package com.example.auth;

import com.example.demo.exception.UserDisabledException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //1、从数据库查询用户
        SysUser sysUser = userMapper.findByUsername(username);
        if (sysUser == null){
            throw new UserNotFoundException("用户不存在");
        }

        //2、校验用户是否启用
        if (!sysUser.isEnabled()) {
            throw new UserDisabledException("用户已被禁用");
        }

        //3、将角色字符串转换为Spring Security 的权限对象
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(sysUser.getRole());

        //4、返回Spring Security 的user对象（参数：用户名、加密密码、权限集合）
        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                Collections.singletonList(authority)
        );

    }























}
