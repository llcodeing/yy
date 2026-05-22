package com.example.demo;

import com.example.demo.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.rmi.AccessException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<String>> handleBusinessException(BusinessException e){
        Result<String> result = new Result<>(e.getCode(),e.getMessage(),null);
        return ResponseEntity.status(e.getCode()).body(result);// 让HTTP状态码和业务码一致

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result<String>> handleValidationException (MethodArgumentNotValidException e){

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("，"));
        Result<String> result = new Result<>(400,"参数校验失败" + message,null);
        return ResponseEntity.badRequest().body(result);

    }
    // 处理权限不足异常（Spring Security 抛出）
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result<String>> handleAccessDenied(AccessDeniedException e){
        Result<String> result = new Result<>(403,"权限不足" + e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(result);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<String>> handleAll(Exception e){
        Result<String> result = new Result<>(500,"服务器内部错误" + e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }






}
