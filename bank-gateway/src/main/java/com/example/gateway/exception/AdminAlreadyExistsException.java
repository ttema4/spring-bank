package com.example.gateway.exception;

public class AdminAlreadyExistsException extends RuntimeException {
    public AdminAlreadyExistsException(String username) {
        super("Администратор с логином '" + username + "' уже существует.");
    }
}