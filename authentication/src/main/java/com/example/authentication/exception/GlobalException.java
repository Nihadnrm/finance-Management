package com.example.authentication.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateUserException.class)
    public Map<String,String>handleDuplicateUserException(DuplicateUserException ex){
     Map<String,String>error=new HashMap<>();
     error.put("Message", ex.getMessage());
     return error;
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String>error=new HashMap<>();
        ex.getBindingResult().getFieldErrors().stream().forEach(i->{
            error.put(i.getField(),i.getDefaultMessage());
        });
        return  error;



}
}
