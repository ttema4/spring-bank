package com.example.gateway.dto;

import java.time.LocalDateTime;

public record OperationDto(
        String description,
        double amount,
        LocalDateTime dateTime
) {}