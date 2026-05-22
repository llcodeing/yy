package com.example.demo.exception;
/**
 * 资源未找到异常，通常对应 HTTP 404
 */
public class ResourceNotFoundException  extends BusinessException{
    public ResourceNotFoundException(String message){
        super(404,message);
    }
}
