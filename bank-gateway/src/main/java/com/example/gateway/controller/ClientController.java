package com.example.gateway.controller;

import com.example.gateway.client.BankSystemAccountsClient;
import com.example.gateway.client.BankSystemUsersClient;
import com.example.gateway.dto.request.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final BankSystemUsersClient usersClient;
    private final BankSystemAccountsClient accountsClient;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyself() {
        return ResponseEntity.ok(usersClient.getUser(getCurrentUsername()));
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getMyAccounts() {
        return ResponseEntity.ok(accountsClient.getUserAccounts(getCurrentUsername()));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(accountsClient.getAccountForClient(getCurrentUsername(), id));
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<?> createAccount(@RequestParam(name = "id") String id) {
        accountsClient.createAccount(getCurrentUsername(), id);
        return ResponseEntity.ok("Счёт успешно создан");
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        accountsClient.deposit(getCurrentUsername(), id, amount);
        return ResponseEntity.ok("Средства успешно внесены");
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        accountsClient.withdraw(getCurrentUsername(), id, amount);
        return ResponseEntity.ok("Средства успешно сняты");
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        accountsClient.transfer(getCurrentUsername(), request);
        return ResponseEntity.ok("Перевод выполнен");
    }

    @PostMapping("/friends/add")
    public ResponseEntity<?> addFriend(@RequestParam(name = "friendLogin") String friendLogin) {
        usersClient.addFriend(getCurrentUsername(), friendLogin);
        return ResponseEntity.ok("Друг добавлен");
    }

    @PostMapping("/friends/remove")
    public ResponseEntity<?> removeFriend(@RequestParam(name = "friendLogin") String friendLogin) {
        usersClient.removeFriend(getCurrentUsername(), friendLogin);
        return ResponseEntity.ok("Друг удалён");
    }
}