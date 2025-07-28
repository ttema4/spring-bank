package com.example.presentation.dto;

public record AccountDto(
        String id,
        String ownerLogin,
        double balance
) {}
