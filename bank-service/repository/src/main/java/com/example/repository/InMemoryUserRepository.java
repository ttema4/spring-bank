package com.example.repository;

import com.example.domain.User;

import java.util.*;

/**
 * Реализация `UserRepository`, хранящая пользователей в памяти (`HashMap`).
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> storage = new HashMap<>();

    /**
     * Создаёт нового пользователя и сохраняет его в памяти.
     *
     * @param user Объект пользователя (не может быть null).
     * @return Созданный пользователь.
     * @throws IllegalArgumentException если пользователь null или логин пустой.
     */
    @Override
    public User createUser(User user) {
        if (user == null || user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Пользователь или его логин не может быть пустым.");
        }
        storage.put(user.getLogin(), user);
        return user;
    }

    /**
     * Ищет пользователя по логину.
     *
     * @param login Логин пользователя (не может быть null или пустым).
     * @return {@code Optional<User>}, содержащий пользователя, если он найден, иначе `Optional.empty()`.
     * @throws IllegalArgumentException если логин пустой.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин не может быть пустым.");
        }
        return Optional.ofNullable(storage.get(login));
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param user Объект пользователя с обновлёнными данными (не может быть null).
     * @throws IllegalArgumentException если пользователь null или логин пустой.
     */
    @Override
    public void updateUser(User user) {
        if (user == null || user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Пользователь или его логин не может быть пустым.");
        }
        if (!storage.containsKey(user.getLogin())) {
            throw new IllegalArgumentException("Пользователь с логином " + user.getLogin() + " не найден.");
        }
        storage.put(user.getLogin(), user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }
}
