package com.example.authentication.services;

import com.example.authentication.dto.*;
import com.example.authentication.entity.AuthUsers;
import com.example.authentication.entity.RefreshToken;
import com.example.authentication.entity.Role;
import com.example.authentication.enums.Status;
import com.example.authentication.exception.DuplicateUserException;
import com.example.authentication.repository.AuthUsersRepository;
import com.example.authentication.repository.RoleRepository;
import com.example.authentication.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthUserService {
    @Autowired
    AuthUsersRepository repo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    RefreshTokenService refreshTokenService;

public ResponseDto register(RegisterDto dto){
   if(repo.existsByEmail(dto.getEmail())){
       throw new DuplicateUserException("user already exists");
   }
    AuthUsers authUsers=new AuthUsers();
    Role role=roleRepo.findByRoleName("CUSTOMER").orElseThrow(()->new RuntimeException("role not found"));
    authUsers.setUserName(dto.getUserName());
    authUsers.setEmail(dto.getEmail());
    authUsers.setPassword(passwordEncoder.encode(dto.getPassword()));
    authUsers.setRole(List.of(role));
    authUsers.setStatus(Status.ACTIVE);
    AuthUsers save=repo.save(authUsers);
    return new ResponseDto(save.getId(),"user added successfully");

}
public List<UsersResponse>getAllUsers(){
    List<AuthUsers>list=repo.findAll();
    List<UsersResponse>listDto=new ArrayList<>();
    for(AuthUsers i:list){
        List<String>roleNames=i.getRole().stream().map(r->r.getRoleName()).toList();
        listDto.add(new UsersResponse(i.getUserName(),i.getEmail(),i.getPassword(),roleNames,i.getStatus(),i.getCreatedAt()));
    }
    return listDto;
}

public LoginResponseDto login(LoginDto dto){
    AuthUsers authUsers=repo.findByEmail(dto.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
    if(!passwordEncoder.matches(dto.getPassword(),authUsers.getPassword())){
        throw new RuntimeException("password not match");
    }
    List<String>roleNames=authUsers.getRole().stream().map(i->i.getRoleName()).toList();

//    return jwtService.generateToken(authUsers.getEmail(),authUsers.getUserName(),authUsers.getId(),roleNames);
    String accessToken= jwtService.generateToken(authUsers.getEmail(),authUsers.getUserName(),authUsers.getId(),roleNames);
    RefreshToken refreshToken=refreshTokenService.createRefreshToken(authUsers);
    return  new LoginResponseDto(accessToken,refreshToken.getRefreshToken());
}

public LoginResponseDto refreshAccessToken(RefreshTokenRequestDto dto){
    RefreshToken refreshToken= refreshTokenService.varifyRefreshToken(dto.getRefreshToken());
    AuthUsers users=refreshToken.getAuthUsers();
    List<String>roleNames=users.getRole().stream().map(i->i.getRoleName()).toList();
    String newAccessToken=jwtService.generateToken(users.getEmail(),users.getUserName(),users.getId(),roleNames);
    return  new LoginResponseDto(newAccessToken,refreshToken.getRefreshToken());
}


public String addRoleName(ExtraRoleDto dto){
AuthUsers authUsers=repo.findByEmail(dto.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
Role role=roleRepo.findByRoleName(dto.getRoleName()).orElseThrow(()->new RuntimeException("role not found"));
boolean exist=authUsers.getRole().stream().anyMatch(i->i.getRoleName().equalsIgnoreCase(dto.getRoleName()));

if(exist){
    throw new RuntimeException("role already exist");
}
authUsers.getRole().add(role);
AuthUsers save=repo.save(authUsers);
return "role added successfully";

}



}
