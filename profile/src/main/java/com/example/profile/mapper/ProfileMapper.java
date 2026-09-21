package com.example.profile.mapper;

import com.example.profile.dto.*;
import com.example.profile.entity.Profile;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toEntity(ProfileRequestDto dto);


    ProfileResponseDto toDto(Profile profile);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "imageUrl",ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(ProfileUpdateDto dto, @MappingTarget Profile profile);

  default   Page<ProfileResponseDto>topage(Page<Profile>data){
      return data.map(row->new ProfileResponseDto(row.getId(),row.getProfileName(),row.getEmail(),row.getUserId(),row.getAddress(),row.getImageUrl(),row.getTotalAmount()));
  }

  @Mapping(target = "id",ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTotal(ProfileTotalUpdateDto dto, @MappingTarget Profile profile);
}
