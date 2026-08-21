package com.example.authentication.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUsers authUsers;

    @Column(unique = true,nullable = false)
    private String refreshToken;

    private LocalDateTime expirationDate;
    private  boolean revoked;

    public RefreshToken() {
    }

    public RefreshToken(Long id, AuthUsers authUsers, String refreshToken, LocalDateTime expirationDate, boolean revoked) {
        this.id = id;
        this.authUsers = authUsers;
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
        this.revoked = revoked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthUsers getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(AuthUsers authUsers) {
        this.authUsers = authUsers;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
