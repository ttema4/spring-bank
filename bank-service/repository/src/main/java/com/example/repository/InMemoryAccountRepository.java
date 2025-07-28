package com.example.repository;

import com.example.domain.Account;

import java.util.*;

/**
 * Реализация `AccountRepository`, хранящая счета в памяти (`HashMap`).
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> storage = new HashMap<>();

    /**
     * Создаёт новый счёт и сохраняет его в памяти.
     *
     * @param account Объект счёта (не может быть null).
     * @return Созданный счёт.
     * @throws IllegalArgumentException если счёт null или ID пустой.
     */
    @Override
    public Account createAccount(Account account) {
        if (account == null || account.getId() == null || account.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Счёт или его ID не может быть пустым.");
        }
        storage.put(account.getId(), account);
        return account;
    }

    /**
     * Ищет счёт по идентификатору.
     *
     * @param accountId Уникальный идентификатор счёта (не может быть null или пустым).
     * @return {@code Optional<Account>}, содержащий счёт, если он найден, иначе `Optional.empty()`.
     * @throws IllegalArgumentException если accountId пустой.
     */
    @Override
    public Optional<Account> findById(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID счёта не может быть пустым.");
        }
        return Optional.ofNullable(storage.get(accountId));
    }

    /**
     * Обновляет данные счёта.
     *
     * @param account Объект счёта с обновлёнными данными (не может быть null).
     * @throws IllegalArgumentException если счёт null, ID пустой или счёт не найден.
     */
    @Override
    public void updateAccount(Account account) {
        if (account == null || account.getId() == null || account.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Счёт или его ID не может быть пустым.");
        }
        if (!storage.containsKey(account.getId())) {
            throw new IllegalArgumentException("Счёт с ID " + account.getId() + " не найден.");
        }
        storage.put(account.getId(), account);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }
}
