package com.example.authentication.services;

import com.example.authentication.entity.AuthUsers;
import com.example.authentication.entity.RefreshToken;
import com.example.authentication.repository.AuthUsersRepository;
import com.example.authentication.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    AuthUsersRepository authUsersRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

public RefreshToken createRefreshToken(AuthUsers users){

    RefreshToken refreshToken=new RefreshToken();
    refreshToken.setAuthUsers(users);
    refreshToken.setRefreshToken(UUID.randomUUID().toString());
    refreshToken.setExpirationDate(LocalDateTime.now().plusDays(30));
    refreshToken.setRevoked(false);
    return refreshTokenRepository.save(refreshToken);

}

public RefreshToken varifyRefreshToken(String token){

    RefreshToken refreshToken=refreshTokenRepository.findByRefreshToken(token).orElseThrow(()->new RuntimeException("refresh token not found"));
    if (refreshToken.isRevoked()){
        throw new RuntimeException("refresh token is revoked");
    }
    if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())){
        throw new RuntimeException("refresh token is expired");
    }
    return refreshToken;
}
public void RevokeRefreshToken(RefreshToken refreshToken){
refreshToken.setRevoked(true);
refreshTokenRepository.save(refreshToken);
}

}
