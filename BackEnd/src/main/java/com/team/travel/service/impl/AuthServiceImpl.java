package com.team.travel.service.impl;

import com.team.travel.dto.auth.AuthRequest;
import com.team.travel.dto.auth.AuthResponse;
import com.team.travel.entity.Token;
import com.team.travel.entity.User;
import com.team.travel.service.AuthService;
import com.team.travel.service.TokenService;
import com.team.travel.service.UserService;
import com.team.travel.security.JwtService;
import com.team.travel.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // Create an authentication token with the provided email and password
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        // Perform authentication using Spring's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(authToken);

        String jwt = jwtService.generateToken(authentication);

        User user = userService.getByEmail(request.getEmail());
        Token token = new Token(user, jwt, false, false);
        tokenService.create(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new AuthResponse(jwt, userDetails.getId(), userDetails.getRole());
    }
}
