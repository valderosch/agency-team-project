package com.team.travel.security;

import com.team.travel.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class JwtLogoutHandler implements LogoutHandler {
    private final TokenService tokenService;
    private final JwtService jwtService;

    public JwtLogoutHandler(TokenService tokenService, JwtService jwtService) {
        this.tokenService = tokenService;
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = jwtService.parseJwt(request);
        if (jwt == null) {
            return;
        }
        tokenService.expireAndRevokeToken(jwt);
        SecurityContextHolder.clearContext();
    }
}
