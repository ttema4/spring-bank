package com.example.repository;

import com.example.domain.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * Реализация репозитория пользователей через Hibernate.
 */
public class UserRepositoryHibernateImpl implements UserRepository {

    @Override
    public User createUser(User user) {
        if (user == null) throw new IllegalArgumentException("Пользователь не может быть null");

        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            try {
                session.persist(user);
                tx.commit();
                return user;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, login));
        }
    }

    @Override
    public void updateUser(User user) {
        if (user == null) throw new IllegalArgumentException("Пользователь не может быть null");

        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            try {
                session.merge(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }
}
