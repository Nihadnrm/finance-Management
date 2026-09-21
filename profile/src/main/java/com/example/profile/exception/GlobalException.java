package com.example.profile.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String>error=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(i->{
            error.put(i.getField(),i.getDefaultMessage());
        });
        return error;
    }

}
