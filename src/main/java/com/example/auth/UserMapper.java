package com.example.auth;



import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, role, enabled FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(String username);

    @Insert("INSERT INTO sys_user (username, password, role, enabled) VALUES (#{username}, #{password}, #{role}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SysUser user);
}