package com.example.gateway.dto.request;

public record TransferRequest(
        String fromId,
        String toId,
        double amount
) {}