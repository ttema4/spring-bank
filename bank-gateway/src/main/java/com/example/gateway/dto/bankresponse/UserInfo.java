package com.example.gateway.dto.bankresponse;

public record UserInfo(
        String login,
        String name,
        int age,
        String gender,
        String hairColor
) {
}
