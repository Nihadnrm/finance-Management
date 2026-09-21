package com.example.amount.mapper;


import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.dto.TotalAmountForUserDto;
import com.example.amount.entity.Amount;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AmountMapper {



    @Mapping(target = "takeBackDate",source = "duration",qualifiedByName = "setTakeBackDate")
    Amount toEntity(AmountRequestDto dto);




    @Named("setTakeBackDate")
    default LocalDateTime setTakeBackDate(int duration){
        return LocalDateTime.now().plusMonths(duration);
    }

   //// we can use qualifiedByName also as afterMapping

//    @AfterMapping()
//     default void afterMapping(AmountRequestDto dto, @MappingTarget Amount amount){
//        amount.setTakeBackDate(LocalDateTime.now().plusMonths(dto.getDuration()));
//    }


    @Mapping(source ="amount" ,target ="amount" ,qualifiedByName ="addAmountSign" )
    AmountResponseDto toDTO(Amount amount);
    @Named("addAmountSign")
    default String addAmountTag(BigDecimal amount){
        return "₹"+amount;
    }

    default Page<AmountResponseDto> toListDto(Page<Amount>amounts){
        return amounts.map(this::toDTO);
    }

   default TotalAmountForUserDto toTotalAmount(Object[] data){

       Object[] row1  =(Object[]) data [0];
       Long userId=(Long) row1[0];
       BigDecimal total=row1[1]!=null?(BigDecimal) row1[1] : BigDecimal.ZERO;
       return  new TotalAmountForUserDto(userId,total);
   }



}
