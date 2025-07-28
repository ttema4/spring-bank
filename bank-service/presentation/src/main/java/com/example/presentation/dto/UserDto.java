package com.example.presentation.dto;

import com.example.domain.HairColor;
import java.util.List;

public record UserDto(
        String login,
        String name,
        int age,
        String gender,
        HairColor hairColor,
        List<String> friendsLogins
) {}
