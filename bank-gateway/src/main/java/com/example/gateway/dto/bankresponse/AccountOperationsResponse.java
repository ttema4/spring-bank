package com.example.gateway.dto.bankresponse;

import java.util.List;

public record AccountOperationsResponse(List<OperationInfo> operations) {
}