package com.example.gateway.service;

import com.example.gateway.client.BankSystemUsersClient;
import com.example.gateway.dto.request.*;
import com.example.gateway.dto.response.AuthResponse;
import com.example.gateway.exception.AdminAlreadyExistsException;
import com.example.gateway.exception.ClientAlreadyExistsException;
import com.example.gateway.model.AppUser;
import com.example.gateway.repository.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;

@Service
@RequiredArgsConstructor
public class LocalAuthServiceImpl implements LocalAuthService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final BankSystemUsersClient usersClient;

    @Override
    public ResponseEntity<String> createClient(RegisterClientRequest request) {
        if (userRepo.existsById(request.username())) {
            throw new ClientAlreadyExistsException(request.username());
        }

        AppUser user = AppUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role("CLIENT")
                .build();
        userRepo.save(user);

        UserCreateRequest bankRequest = new UserCreateRequest(
                request.username(),
                request.name(),
                request.age(),
                request.gender(),
                request.hairColor()
        );

        usersClient.createClientInBank(bankRequest);
        return ResponseEntity.ok("Пользователь успешно создан.");
    }

    @Override
    public ResponseEntity<String> createAdmin(RegisterAdminRequest request) {
        if (userRepo.existsById(request.username())) {
            throw new AdminAlreadyExistsException(request.username());
        }

        AppUser admin = AppUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role("ADMIN")
                .build();

        userRepo.save(admin);
        return ResponseEntity.ok("Админ успешно создан.");
    }

    @Override
    public ResponseEntity<String> whoAmI() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String authorities = auth.getAuthorities().toString();
        return ResponseEntity.ok("Пользователь: " + username + ", роли: " + authorities);
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            httpRequest.getSession(true)
                    .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            return ResponseEntity.ok(new AuthResponse("Аутентификация успешна"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Неверные данные"));
        }
    }

    @Override
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new AuthResponse("Вы вышли из системы"));
    }
}