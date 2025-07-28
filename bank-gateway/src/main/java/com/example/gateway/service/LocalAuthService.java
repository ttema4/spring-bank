package com.example.gateway.service;

import com.example.gateway.dto.request.AuthRequest;
import com.example.gateway.dto.request.RegisterAdminRequest;
import com.example.gateway.dto.request.RegisterClientRequest;
import com.example.gateway.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface LocalAuthService {
    ResponseEntity<String> createClient(RegisterClientRequest request);
    ResponseEntity<String> createAdmin(RegisterAdminRequest request);

    ResponseEntity<String> whoAmI();
    ResponseEntity<AuthResponse> login(AuthRequest request, HttpServletRequest httpRequest);
    ResponseEntity<AuthResponse> logout(HttpServletRequest request);
}