package com.example.profile.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    @Size(min = 3,max = 250,message = "over length of characters")
    private String profileName;
    private  String email;
    private Long userId;

}
