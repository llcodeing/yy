package com.example.demo.exception;
/**
 * 权限不足异常，通常对应 HTTP 403
 */
public class ForbiddenException extends BusinessException{
    public ForbiddenException(String message){
        super(403,message);
    }
}
