package com.example.storage.dto;

public record AccountCreatedEvent(
        String id,
        String ownerLogin,
        double balance
) {}