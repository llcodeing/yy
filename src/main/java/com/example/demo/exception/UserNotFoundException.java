package com.example.demo.exception;

/**
 * 用户不存在异常
 */
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(404, message);
    }
}