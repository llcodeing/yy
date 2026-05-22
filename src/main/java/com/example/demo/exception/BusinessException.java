package com.example.demo.exception;
/**
 * 自定义业务异常基类，所有业务异常应继承此类。
 * 可以在构造时传入错误码和消息，方便统一处理。
 */
public class BusinessException extends RuntimeException{
    private final int code;
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode(){ return code;}

}
