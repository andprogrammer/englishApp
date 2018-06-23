package com.english;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final String HIBERNATE_CFG = "hibernate.cfg.xml";

    private static SessionFactory buildSessionFactory()
    {
        Configuration cfg = new Configuration().addResource(HIBERNATE_CFG);
        cfg.addAnnotatedClass(Customer.class);
        cfg.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
