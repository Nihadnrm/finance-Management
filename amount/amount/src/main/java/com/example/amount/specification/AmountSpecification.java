package com.example.amount.specification;

import com.example.amount.entity.Amount;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class AmountSpecification {
   public  static Specification<Amount>priceBetween(BigDecimal min,BigDecimal max){
       return (root,query,criteriaBuilder)->
               criteriaBuilder.between(root.get("amount"),min,max);
   }

   public  static  Specification<Amount>duration(int duration){

       return (root, query, criteriaBuilder) ->
               criteriaBuilder.greaterThan(root.get("duration"),duration);
   }
}


//ellamkoode onnil using and
//----------------------------


//Specification<Amount> condi (BigDecimal min,BigDecimal max,int duration){
//        (root, query, cb) ->
//                cb.and(
//                        cb.between(root.get("amount"), min, max),
//                        cb.equal(root.get("duration"), 12)
//                );
//}


//ellamkoode onnil using or
//-----------------------------

//Specification<Amount> condi (BigDecimal min,BigDecimal max,int duration){
//        (root, query, cb) ->
//                cb.or(
//                        cb.between(root.get("amount"), min, max),
//                        cb.equal(root.get("duration"), 12)
//                );
//}
