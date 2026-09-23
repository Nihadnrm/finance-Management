package com.example.contact.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponseDto {

    private Long id;
    private Long userId;
    private  String userName;
    private  String message;
}
