package com.english;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBHandler {

    private static final Logger LOGGER = Logger.getLogger(DBHandler.class.getName());

    public static void addNewCustomerToDB(Customer customer) {
        try
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(customer);
            LOGGER.log(Level.FINE, "Save new customer {0} to DB", customer.getFirstName());
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            e.getStackTrace();
        }
    }

    public static void updateCustomerInDB(Customer customer) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.update(customer);
            LOGGER.log(Level.FINE, "Update customer {0} in DB", customer.getFirstName());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
    }

    public static List<Customer> getAllCustomersFromDB() {
        try
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery("SELECT * FROM customer").addEntity(Customer.class).list();
            LOGGER.log(Level.FINE, "Processing {0} customers from DB", customers.size());
            session.close();
            return customers;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return Collections.emptyList();
    }

    public static Optional<Customer> getSingleCustomer(String email, String password) {
        Customer customer = null;
        try
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery("SELECT * FROM customer where customer_email='" + email + "' AND customer_password='" + password + "'").addEntity(Customer.class).list();
            if(customers.size() > 0) customer = customers.get(0);
            session.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    public static Optional<Customer> getSingleCustomer(String email) {
        Customer customer = null;
        try
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery("SELECT * FROM customer where customer_email='" + email + "'").addEntity(Customer.class).list();
            if(customers.size() > 0) customer = customers.get(0);
            session.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return Optional.ofNullable(customer);
    }
}
