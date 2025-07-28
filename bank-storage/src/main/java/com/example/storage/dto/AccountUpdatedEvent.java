package com.example.storage.dto;

public record AccountUpdatedEvent(
        String id,
        String ownerLogin,
        double balance
) {}