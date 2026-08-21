package com.example.authentication.dto;

import com.example.authentication.entity.Role;
import com.example.authentication.enums.Status;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.util.List;

public class UsersResponse {
    private String userName;
    private String email;
    private String password;
    private List<String> roleName;
    private Status status;
    private LocalDateTime createdAt;

    public UsersResponse() {
    }

    public UsersResponse(String userName, String email, String password, List<String> roleName, Status status, LocalDateTime createdAt) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleName = roleName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
