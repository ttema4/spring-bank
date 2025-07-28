package com.example.gateway.dto.bankresponse;

public record OperationInfo(String id, String type, double amount, String timestamp) {
}