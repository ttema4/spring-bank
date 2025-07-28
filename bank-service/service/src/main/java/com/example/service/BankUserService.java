package com.example.service;

import com.example.domain.User;

import java.util.List;
import java.util.Optional;

public interface BankUserService {

    User createUser(String login, String name, int age, String gender, String hairColor);

    void addFriend(String userLogin, String friendLogin);

    void removeFriend(String userLogin, String friendLogin);

    Optional<User> getUser(String login);

    List<User> getAllUsers();
}
