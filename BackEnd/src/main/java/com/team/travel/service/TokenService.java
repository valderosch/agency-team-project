package com.team.travel.service;

import com.team.travel.entity.Token;

public interface TokenService {
    void create(Token token);
    boolean isTokenValid(String token);
    void expireToken(String token);
    void expireAndRevokeToken(String token);
}
