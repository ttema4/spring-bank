package com.example.storage.dto;

import java.util.List;

public record UserUpdatedEvent(
        String login,
        String name,
        int age,
        String gender,
        String hairColor,
        List<String> friendsLogins
) {}