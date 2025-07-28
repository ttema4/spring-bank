package com.example.kafka.dto;

public record AccountCreatedEvent(
        String id,
        String ownerLogin,
        double balance
) {}