package com.example.gateway.dto.request;

public record CreateAccountRequest(
        String id,
        String ownerLogin
) {}