package com.example.demo;

import com.example.auth.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLoadUser() {
        // 应该能正常加载
        UserDetails user = userService.loadUserByUsername("admin");
        System.out.println("用户名: " + user.getUsername());
        System.out.println("密码: " + user.getPassword());
        System.out.println("角色: " + user.getAuthorities());
    }
}