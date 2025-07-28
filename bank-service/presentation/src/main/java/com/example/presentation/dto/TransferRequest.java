package com.example.presentation.dto;

public record TransferRequest(
        String fromId,
        String toId,
        double amount
) {}
