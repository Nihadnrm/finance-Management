package com.example.authentication.controller;

import com.example.authentication.dto.*;
import com.example.authentication.services.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthUserService service;

    @PostMapping("/register")
    public ResponseDto register(@Valid @RequestBody RegisterDto dto){
        return service.register(dto);
    }
    @GetMapping("/register")
    public List<UsersResponse>getAllUsers(){
        return service.getAllUsers();
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto dto){
        return service.login(dto);
    }
    @PostMapping("/addrole")
    public String addRoleName(@RequestBody ExtraRoleDto dto){
        return service.addRoleName(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto>refreshAccessToken(@RequestBody RefreshTokenRequestDto dto){
       return ResponseEntity.ok(service.refreshAccessToken(dto));
    }
}
