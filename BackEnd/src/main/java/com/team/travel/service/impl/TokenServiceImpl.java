package com.team.travel.service.impl;

import com.team.travel.entity.Token;
import com.team.travel.exception.InvalidTokenException;
import com.team.travel.repository.TokenRepository;
import com.team.travel.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository repository;

    public TokenServiceImpl(TokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Token token) {
        repository.save(token);
    }

    @Override
    public boolean isTokenValid(String token) {
        Token databaseToken = repository.findByToken(token).orElse(null);
        if (databaseToken == null) {
            throw new InvalidTokenException("Token is missing or not provided");
        }
        else if (databaseToken.isRevoked()) {
            throw new InvalidTokenException("The token has been revoked");
        }
        else if (databaseToken.isExpired()) {
            throw new InvalidTokenException("The token has expired");
        }
        return true;
    }

    @Override
    public void expireToken(String token) {
        updateTokenStatus(token, true, false);
    }

    @Override
    public void expireAndRevokeToken(String token) {
        updateTokenStatus(token, true, true);
    }

    private void updateTokenStatus(String token, boolean expired, boolean revoked) {
        Token databaseToken = repository.findByToken(token).orElse(null);

        if (databaseToken != null) {
            databaseToken.setExpired(expired);
            databaseToken.setRevoked(revoked);
            repository.save(databaseToken);
        }
    }
}
