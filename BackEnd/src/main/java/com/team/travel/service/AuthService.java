package com.team.travel.service;

import com.team.travel.dto.auth.AuthRequest;
import com.team.travel.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
}
