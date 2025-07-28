package com.example.gateway.dto;

public record AccountDto(
        String id,
        String ownerLogin,
        double balance
) {}