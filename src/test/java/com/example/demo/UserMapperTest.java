package com.example.demo;


import com.example.auth.SysUser;
import com.example.auth.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAdmin() {
        SysUser user = userMapper.findByUsername("admin");
        System.out.println("===== 诊断开始 =====");
        System.out.println("用户名: " + user.getUsername());
        System.out.println("密码密文: " + user.getPassword());
        System.out.println("角色: " + user.getRole());
        System.out.println("enabled 字段值（isEnabled()）: " + user.isEnabled());
        System.out.println("===== 诊断结束 =====");
    }
}