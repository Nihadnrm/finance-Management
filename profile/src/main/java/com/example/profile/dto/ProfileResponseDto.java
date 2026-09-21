package com.example.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String profileName;
    private  String email;
    private Long userId;
    private String address;
    private String imageUrl;
    private BigDecimal totalAmount;
}
