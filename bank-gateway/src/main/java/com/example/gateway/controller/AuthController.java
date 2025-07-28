package com.example.gateway.controller;

import com.example.gateway.dto.request.AuthRequest;
import com.example.gateway.dto.response.AuthResponse;
import com.example.gateway.service.LocalAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final LocalAuthService authService;

    @GetMapping("/whoami")
    public ResponseEntity<String> whoAmI() {
        return authService.whoAmI();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}
