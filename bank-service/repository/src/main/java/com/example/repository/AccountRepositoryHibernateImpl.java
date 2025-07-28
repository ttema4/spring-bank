package com.example.repository;

import com.example.domain.Account;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * Реализация репозитория аккаунтов через Hibernate.
 */
public class AccountRepositoryHibernateImpl implements AccountRepository {

    @Override
    public Account createAccount(Account account) {
        if (account == null) throw new IllegalArgumentException("Счёт не может быть null");

        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            try {
                session.persist(account);
                tx.commit();
                return account;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public Optional<Account> findById(String accountId) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Account.class, accountId));
        }
    }

    @Override
    public void updateAccount(Account account) {
        if (account == null) throw new IllegalArgumentException("Счёт не может быть null");

        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            try {
                session.merge(account);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Account> findAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("from Account", Account.class).list();
        }
    }
}
