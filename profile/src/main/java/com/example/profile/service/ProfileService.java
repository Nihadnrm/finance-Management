package com.example.profile.service;

import com.example.profile.dto.*;
import com.example.profile.entity.Profile;
import com.example.profile.mapper.ProfileMapper;
import com.example.profile.repository.ProfileRepository;
import com.example.profile.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class ProfileService {
    @Autowired
    ProfileRepository repo;
    @Autowired
    JwtService jwtService;
    @Autowired
    ProfileMapper mapper;



    void  adminRoleCheck(String token){
        List<String>roles=jwtService.extractRoleNames(token.substring(7));
        if(!roles.contains("ADMIN")){
            throw  new RuntimeException("role not found");
        }
    }



    public ProfileResponseDto createProfile(ProfileRequestDto dto) {
        if(repo.existsByUserId(dto.getUserId())){
            throw  new RuntimeException("user profile already exist");
        }

       Profile profile= mapper.toEntity(dto);
        if(profile.getTotalAmount()==null){
            profile.setTotalAmount(BigDecimal.ZERO);
        }

        Profile save=repo.save(profile);
        return mapper.toDto(save);

    }

    public Page<ProfileResponseDto> getProfileList(String token,int page,int size){
        adminRoleCheck(token);
        Pageable pageable= PageRequest.of(page,size);
        Page<Profile>pages=repo.findAll(pageable);
        return mapper.topage(pages);
    }
    public ProfileResponseDto getByUserId(String token){
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        Profile profile=repo.getByUserId(referenceId).orElseThrow(()->new RuntimeException("can not find profile"));
        return mapper.toDto(profile);
    }


    public ProfileResponseDto updateProfile(String token, ProfileUpdateDto dto)throws IOException {
        Long referenceId=jwtService.extractReferenceId(token.substring(7));


        Profile profile=repo.getByUserId(referenceId).orElseThrow(()->new RuntimeException("profile can not find for this user"));

        mapper.updateProfile(dto,profile);


        MultipartFile image=dto.getImage();

        if(image!=null&&!image.isEmpty()){

            String fileName= UUID.randomUUID()+"_"+image.getOriginalFilename();
            Path path= Paths.get("uploads/profileImage/"+fileName);
            Files.copy(image.getInputStream(),path);
            profile.setImageUrl("/uploads/profileImage/"+fileName);

        }
        Profile update=repo.save(profile);
        return mapper.toDto(update);

    }

    public ProfileResponseDto updateTotal(String token, ProfileTotalUpdateDto dto){
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        Profile profile=repo.getByUserId(referenceId).orElseThrow(()->new RuntimeException("profile not found"));
        mapper.updateTotal(dto,profile);
        Profile update=repo.save(profile);
        return mapper.toDto(update);
    }

    public Page<ProfileResponseDto>searchByPName(String token,String pName,int page,int size){
        adminRoleCheck(token);
        Pageable pageable=PageRequest.of(page,size);
        Page<Profile>pages=repo.searchByName(pageable,pName);
        return mapper.topage(pages);
    }
}
