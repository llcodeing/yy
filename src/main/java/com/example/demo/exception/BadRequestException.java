package com.example.demo.exception;

/**
 * 客户端请求错误，通常对应 HTTP 400
 */
public class BadRequestException extends BusinessException{
    public BadRequestException(String message){
        super(400,message);
    }

}
