package com.example.gateway.exception;

public class AccessDeniedToAccountException extends RuntimeException {
    public AccessDeniedToAccountException(String accountId) {
        super("У вас нет доступа к счёту с ID '" + accountId + "'.");
    }
}