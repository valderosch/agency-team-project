package com.team.travel.dto.auth;

import com.team.travel.entity.Role;

public class AuthResponse {
    private String token; //access jwt token
    private Long id;
    private Role role;

    public AuthResponse() {
    }

    public AuthResponse(String token, Long id, Role role) {
        this.token = token;
        this.id = id;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}
