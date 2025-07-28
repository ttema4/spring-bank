package com.example.presentation.controller;

import com.example.presentation.dto.OperationDto;
import com.example.domain.Account;
import com.example.presentation.dto.AccountCreateRequest;
import com.example.presentation.dto.AccountDto;
import com.example.presentation.dto.TransferRequest;
import com.example.presentation.mapper.MapperUtil;
import com.example.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final BankAccountService bankAccountService;

    public AccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(summary = "Создание счёта")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счёт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации или пользователь не найден")
    })
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountCreateRequest request) {
        Account account = bankAccountService.createAccount(request.id(), request.ownerLogin());
        return ResponseEntity.ok(MapperUtil.toAccountDto(account));
    }

    @Operation(summary = "Получение счёта по ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") String id) {
        return bankAccountService.getAccount(id)
                .map(MapperUtil::toAccountDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Пополнение счёта")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable("id") String id,
            @RequestParam(name = "amount") double amount) {
        bankAccountService.deposit(id, amount);
        return ResponseEntity.ok("Пополнение успешно");
    }

    @Operation(summary = "Снятие со счёта")
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable("id") String id,
            @RequestParam(name = "amount") double amount) {
        bankAccountService.withdraw(id, amount);
        return ResponseEntity.ok("Снятие успешно");
    }

    @Operation(summary = "Перевод между счетами")
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        bankAccountService.transfer(request.fromId(), request.toId(), request.amount());
        return ResponseEntity.ok("Перевод успешно выполнен");
    }

    @Operation(summary = "Получение всех счетов")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> list = bankAccountService.getAllAccounts()
                .stream().map(MapperUtil::toAccountDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Получение операций по счёту")
    @GetMapping("/{id}/operations")
    public ResponseEntity<List<OperationDto>> getOperations(
            @PathVariable("id") String id,
            @RequestParam(name = "type", required = false) String type) {

        return bankAccountService.getAccount(id)
                .map(acc -> {
                    List<OperationDto> ops = acc.getOperations().stream()
                            .filter(op -> type == null || op.getDescription().equalsIgnoreCase(type))
                            .map(MapperUtil::toOperationDto)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(ops);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение всех счетов пользователя")
    @GetMapping("/user/{login}")
    public ResponseEntity<List<AccountDto>> getUserAccounts(@PathVariable("login") String login) {
        List<AccountDto> accounts = bankAccountService.getAllAccounts().stream()
                .filter(a -> a.getOwnerLogin().equals(login))
                .map(MapperUtil::toAccountDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(accounts);
    }
}
