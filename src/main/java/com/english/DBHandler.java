package com.english;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBHandler {

    private static final Logger LOGGER = Logger.getLogger(DBHandler.class.getName());

    public static void AddNewCustomerToDB(Customer customer) {
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

    public static List<Customer> GetCustomerFromDB() {
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
}
