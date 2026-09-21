package com.example.profile.repository;

import com.example.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

  Boolean existsByUserId(Long userId);
  Optional <Profile> getByUserId(long userId);

}
