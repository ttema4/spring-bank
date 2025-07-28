package com.example.kafka.mapper;

import com.example.domain.Account;
import com.example.domain.User;
import com.example.kafka.dto.*;

public class KafkaMapper {

    public static AccountCreatedEvent toAccountCreatedEvent(Account account) {
        return new AccountCreatedEvent(
                account.getId(),
                account.getOwnerLogin(),
                account.getBalance()
        );
    }

    public static AccountUpdatedEvent toAccountUpdatedEvent(Account account) {
        return new AccountUpdatedEvent(
                account.getId(),
                account.getOwnerLogin(),
                account.getBalance()
        );
    }

    public static UserCreatedEvent toUserCreatedEvent(User user) {
        return new UserCreatedEvent(
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor().name(),
                user.getFriendsLogins()
        );
    }

    public static UserUpdatedEvent toUserUpdatedEvent(User user) {
        return new UserUpdatedEvent(
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor().name(),
                user.getFriendsLogins()
        );
    }
}