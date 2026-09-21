package com.example.amount.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountResponseDto {
    private  Long id;
    private String amount;
    private LocalDateTime depositDate;
    private int duration;
    private LocalDateTime takeBackDate;
    private Long userId;


}
