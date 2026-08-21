package com.example.amount.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public interface GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    default Map<String,String>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
       Map<String,String>error=new HashMap<>();
       ex.getBindingResult().getFieldErrors().forEach(i->{
           error.put(i.getField(),i.getDefaultMessage());
       });
       return  error;
    }

}
