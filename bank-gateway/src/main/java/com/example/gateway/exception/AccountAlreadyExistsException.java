package com.example.gateway.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String accountId) {
        super("Счёт с ID '" + accountId + "' уже существует.");
    }
}