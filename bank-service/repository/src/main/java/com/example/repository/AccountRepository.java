package com.example.repository;

import com.example.domain.Account;
import java.util.Optional;
import java.util.List;

/**
 * Репозиторий для управления банковскими счетами.
 */
public interface AccountRepository {

    /**
     * Создаёт новый счёт.
     *
     * @param account Объект счёта (не может быть null).
     * @return Созданный счёт.
     * @throws IllegalArgumentException если передан null.
     */
    Account createAccount(Account account);

    /**
     * Ищет счёт по идентификатору.
     *
     * @param accountId Уникальный идентификатор счёта (не может быть null или пустым).
     * @return Optional, содержащий найденный счёт, либо пустой, если счёт не найден.
     */
    Optional<Account> findById(String accountId);

    /**
     * Обновляет данные счёта.
     *
     * @param account Объект счёта с обновлёнными данными (не может быть null).
     * @throws IllegalArgumentException если передан null.
     */
    void updateAccount(Account account);

    /**
     * Возвращает список всех счетов.
     *
     * @return Список счетов.
     */
    List<Account> findAll();
}
