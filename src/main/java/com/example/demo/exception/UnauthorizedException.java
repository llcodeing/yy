package com.example.demo.exception;
/**
 * 未授权异常，通常对应 HTTP 401
 */
public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(String message){
        super(401,message);

    }
}
