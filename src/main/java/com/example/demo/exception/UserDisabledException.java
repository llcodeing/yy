package com.example.demo.exception;

/**
 * 用户已被禁用异常
 */
public class UserDisabledException extends BusinessException {
    public UserDisabledException(String message) {
        super(403, message);   // 禁用通常可视为权限不足
    }
}