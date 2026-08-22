package com.example.amount.mapper;


import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.entity.Amount;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AmountMapper {

//    @Mapping(source = "",target=", ignore = true)
    Amount toEntity(AmountRequestDto dto);
    @AfterMapping
     default void afterMapping(AmountRequestDto dto, @MappingTarget Amount amount){
        amount.setTakeBackDate(LocalDateTime.now().plusMonths(dto.getDuration()));
    }

    @Mapping(source ="amount" ,target ="amount" ,qualifiedByName ="addAmountSign" )
    AmountResponseDto toDTO(Amount amount);
    @Named("addAmountSign")
    default String addAmountTag(BigDecimal amount){
        return "₹"+amount;
    }

    List<AmountResponseDto> toListDto(List<Amount>amounts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount",ignore = true)
    @Mapping(target = "duration",ignore = true)
    void updateAmount(AmountRequestDto dto, @MappingTarget Amount amount);
    @AfterMapping
    default void afterUpdateAmountMapping(AmountRequestDto dto,@MappingTarget Amount amount){
       amount.setAmount(amount.getAmount().add(dto.getAmount()));
       amount.setDuration(amount.getDuration()+dto.getDuration());
    }

}
