package com.example.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Единый поставщик SessionFactory (синглтон).
 */
public class SessionFactoryProvider {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при инициализации SessionFactory", ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
