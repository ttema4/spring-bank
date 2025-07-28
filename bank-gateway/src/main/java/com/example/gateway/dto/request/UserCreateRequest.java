package com.example.gateway.dto.request;

public record UserCreateRequest(
        String login,
        String name,
        int age,
        String gender,
        String hairColor
) {}
