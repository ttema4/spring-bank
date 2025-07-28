package com.example.gateway.dto.request;

public record RegisterClientRequest(
        String username,
        String password,
        String name,
        int age,
        String gender,
        String hairColor
) {}