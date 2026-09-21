package com.example.profile.controller;

import com.example.profile.dto.*;
import com.example.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    ProfileService service;


    @PostMapping("/create")
    public ProfileResponseDto createProfile(@RequestBody ProfileRequestDto dto){
        return service.createProfile(dto);
    }
    @GetMapping("")
    public Page<ProfileResponseDto>getProfileList(@RequestHeader("Authorization")String token,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        return  service.getProfileList(token,page,size);
    }
    @GetMapping("/byuserid")
    public ProfileResponseDto getByUserId(@RequestHeader("Authorization")String token){
        return service.getByUserId(token);
    }
    @PutMapping("")
    public ProfileResponseDto updateProfile(@RequestHeader("Authorization")String token, @Valid@ModelAttribute ProfileUpdateDto dto)throws IOException {
     return  service.updateProfile(token,dto);
    }
    @PutMapping("/updatetotal")
    public ProfileResponseDto updateTotal(@RequestHeader("Authorization")String token, @Valid@RequestBody ProfileTotalUpdateDto dto){
    return  service.updateTotal(token,dto);
    }
    @GetMapping("/searchByPName")
    public Page<ProfileResponseDto> searchByPName(@RequestHeader("Authorization")String token,@RequestParam String pName,@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3")int size){
        return service.searchByPName(token,pName,page,size);
    }
}
