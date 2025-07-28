package com.example.kafka.dto;

public record AccountUpdatedEvent(
        String id,
        String ownerLogin,
        double balance
) {}