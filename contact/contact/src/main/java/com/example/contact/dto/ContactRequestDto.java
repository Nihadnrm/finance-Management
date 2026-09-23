package com.example.contact.dto;

import jakarta.persistence.Entity;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDto {

    @NonNull
    private  String message;
}
