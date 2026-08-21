package com.example.authentication.repository;

import com.example.authentication.entity.AuthUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUsersRepository extends JpaRepository<AuthUsers,Long> {

    Boolean existsByEmail(String email);
    Optional<AuthUsers>findByEmail(String email);

}
