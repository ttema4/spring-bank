package com.example.repository;

import com.example.domain.User;
import java.util.Optional;
import java.util.List;

/**
 * Репозиторий для управления пользователями.
 */
public interface UserRepository {

    /**
     * Создаёт нового пользователя.
     *
     * @param user Объект пользователя (не может быть null).
     * @return Созданный пользователь.
     * @throws IllegalArgumentException если пользователь null.
     */
    User createUser(User user);

    /**
     * Ищет пользователя по логину.
     *
     * @param login Логин пользователя (не может быть null или пустым).
     * @return Optional, содержащий найденного пользователя, либо пустой, если пользователь не найден.
     */
    Optional<User> findByLogin(String login);

    /**
     * Обновляет данные пользователя.
     *
     * @param user Объект пользователя с обновлёнными данными (не может быть null).
     * @throws IllegalArgumentException если пользователь null.
     */
    void updateUser(User user);

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список пользователей.
     */
    List<User> findAll();
}
