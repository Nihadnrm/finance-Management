package com.example.profile.repository;

import com.example.profile.dto.ProfileResponseDto;
import com.example.profile.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProfileRepository {

    Page<Profile>searchByName(Pageable pageable, String pName);
}
