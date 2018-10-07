package com.english;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBHandler {

    public enum PROCEDURE_TYPE {
        SAVE, UPDATE, DELETE
    }

    private static final Logger LOGGER = Logger.getLogger(DBHandler.class.getName());

    public static void saveCustomer(Customer customer) {
        executeProcedure(customer, PROCEDURE_TYPE.SAVE, "Save new customer {0} to DB");
    }

    public static void updateCustomer(Customer customer) {
        executeProcedure(customer, PROCEDURE_TYPE.UPDATE, "Update customer {0} in DB");
    }

    public static void deleteCustomer(Customer customer) {
        executeProcedure(customer, PROCEDURE_TYPE.DELETE, "Delete customer {0} to from DB");
    }

    private static void executeProcedure(Customer customer, PROCEDURE_TYPE procedureType, String description) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            switch(procedureType) {
                case SAVE:
                    session.save(customer);
                    break;
                case UPDATE:
                    session.update(customer);
                    break;
                case DELETE:
                    session.delete(customer);
                    break;
            }

            LOGGER.log(Level.FINE, description, customer.getContactMe());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
    }

    public static List<Customer> getAllCustomersFromDB() {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery("SELECT * FROM customer").addEntity(Customer.class).list();
            LOGGER.log(Level.FINE, "Processing {0} customers from DB", customers.size());
            session.close();
            return customers;
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return Collections.emptyList();
    }

    public static boolean checkIfEmailExist(String email) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery("SELECT * FROM customer WHERE customer_email='" + email + "'").addEntity(Customer.class).list();
            LOGGER.log(Level.FINE, "Processing {0} customers from DB", customers.size());
            session.close();
            return customers.size() > 0 ? true : false;
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return false;
    }

    public static Optional<Customer> getSingleCustomer(String email, String password) {
        String query = "SELECT * FROM customer where customer_contact_me='" + email + "' AND customer_password='" + password + "'";
        return getCustomer(query);
    }

    public static Optional<Customer> getSingleCustomer(String email) {
        String query = "SELECT * FROM customer where customer_contact_me='" + email + "'";
        return getCustomer(query);
    }

    private static Optional<Customer> getCustomer(String query) {
        Customer customer = null;
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession();
            List<Customer> customers = session.createSQLQuery(query).addEntity(Customer.class).list();
            if(customers.size() > 0) customer = customers.get(0);
            session.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
        return Optional.ofNullable(customer);
    }
}
