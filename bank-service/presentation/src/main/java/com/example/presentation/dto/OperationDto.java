package com.example.presentation.dto;

import java.time.LocalDateTime;

public record OperationDto(
        LocalDateTime dateTime,
        String description,
        double amount
) {}
