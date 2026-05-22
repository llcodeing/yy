//package com.example.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@Profile("dev")
//public class DataInitializer implements CommandLineRunner {
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception{
//        //创建admin 用户（若不存在）
//        if (userMapper.findByUsername("admin") == null){
//            SysUser admin = new SysUser();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("123456"));
//            admin.setRole("ROLE_ADMIN");
//            admin.setEnabled(true);
//            userMapper.insert(admin);
//        }
//
//        if(userMapper.findByUsername("liuli") == null){
//            SysUser liuli = new SysUser();
//            liuli.setUsername("liuli");
//            liuli.setPassword(passwordEncoder.encode("123456"));
//            System.out.print(passwordEncoder.encode("123456"));
//            liuli.setRole("ROLE_USER");
//            liuli.setEnabled(true);
//            userMapper.insert(liuli);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
