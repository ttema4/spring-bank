package com.example.presentation.mapper;

import com.example.domain.*;
import com.example.presentation.dto.AccountDto;
import com.example.presentation.dto.BalanceDto;
import com.example.presentation.dto.OperationDto;
import com.example.presentation.dto.UserDto;

public class MapperUtil {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor(),
                user.getFriendsLogins()
        );
    }

    public static AccountDto toAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getOwnerLogin(),
                account.getBalance()
        );
    }

    public static OperationDto toOperationDto(Operation op) {
        return new OperationDto(
                op.getDateTime(),
                op.getDescription(),
                op.getAmount()
        );
    }

    public static BalanceDto toBalanceDto(Account acc) {
        return new BalanceDto(acc.getBalance());
    }
}
