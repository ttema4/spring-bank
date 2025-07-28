package com.example.presentation.dto;

public record AccountCreateRequest(
        String id,
        String ownerLogin
) {}
