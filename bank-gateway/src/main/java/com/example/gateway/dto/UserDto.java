package com.example.gateway.dto;

public record UserDto(
        String login,
        String name,
        int age,
        String gender,
        String hairColor
) {}