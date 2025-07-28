package com.example.presentation.dto;

/**
 * DTO для создания пользователя
 */
public record UserCreateRequest(
        String login,
        String name,
        int age,
        String gender,
        String hairColor
) {}
