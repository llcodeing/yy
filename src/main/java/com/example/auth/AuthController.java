package com.example.auth;


import com.example.demo.Result;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public Result<String> register(@RequestBody AuthRequest request){
        // 1. 基本参数校验
        String username = request.getUsername();
        String passoerd = request.getPassword();

        if (username == null|| username.isBlank()){
            return Result.fail("用户名不能为空");
        }
        if (passoerd == null || passoerd.length()<6){
            return Result.fail("密码不能少于6位");
        }
        // 2. 校验用户名唯一
        SysUser existing = userMapper.findByUsername(username);
        if (existing != null){
            return Result.fail("用户名已存在");
        }
        // 3. 创建用户对象
        SysUser newUser = new SysUser();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(passoerd));// BCrypt 加密
        newUser.setRole("ROLE_UESR");
        newUser.setEnabled(true);
        //4.写入数据库
        userMapper.insert(newUser);
        return Result.success("注册成功");

    }

    @PostMapping("/login")
    public Result<AuthResponse> login(@RequestBody AuthRequest request){
        // 1. 调用认证管理器进行用户名/密码验证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // 2. 获取认证成功的用户详情
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. 提取角色（多个角色用逗号拼接，此处只有一个）
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // 4. 生成 JWT 并返回
        String token = jwtUtil.generateToken(userDetails.getUsername(),role);
        return Result.success(new AuthResponse(token));


    }

}
