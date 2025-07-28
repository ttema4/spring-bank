package com.example.gateway.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String username) {
        super("Пользователь с логином '" + username + "' уже существует.");
    }
}