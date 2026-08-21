package com.example.authentication.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GmailValidator implements ConstraintValidator<Gmail,String> {
public boolean isValid(String value, ConstraintValidatorContext context){

    System.out.println("Validator running : "+ value);
   if (value==null){
       return  false;
   }
   return value.endsWith("@gmail.com");

}
}
