package com.example.gateway.controller;

import com.example.gateway.client.BankSystemAccountsClient;
import com.example.gateway.client.BankSystemUsersClient;
import com.example.gateway.dto.request.RegisterAdminRequest;
import com.example.gateway.dto.request.RegisterClientRequest;
import com.example.gateway.service.LocalAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LocalAuthService authService;
    private final BankSystemUsersClient usersClient;
    private final BankSystemAccountsClient accountsClient;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody RegisterClientRequest request) {
        return authService.createClient(request);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody RegisterAdminRequest request) {
        return authService.createAdmin(request);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(name = "gender", required = false) String gender,
                                      @RequestParam(name = "hairColor", required = false) String hairColor) {
        return ResponseEntity.ok(usersClient.getUsers(gender, hairColor));
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<?> getUser(@PathVariable(name = "login") String login) {
        return ResponseEntity.ok(usersClient.getUser(login));
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountsClient.getAllAccounts());
    }

    @GetMapping("/accounts/user/{login}")
    public ResponseEntity<?> getUserAccounts(@PathVariable(name = "login") String login) {
        return ResponseEntity.ok(accountsClient.getUserAccounts(login));
    }

    @GetMapping("/accounts/{id}/operations")
    public ResponseEntity<?> getAccountOperations(@PathVariable(name = "id") String id,
                                                  @RequestParam(name = "type", required = false) String type) {
        return ResponseEntity.ok(accountsClient.getAccountOperations(id, type));
    }
}