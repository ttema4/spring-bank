package com.example.gateway.dto.bankresponse;

import java.util.List;

public record UserAccountListResponse(List<AccountInfo> accounts) {
}