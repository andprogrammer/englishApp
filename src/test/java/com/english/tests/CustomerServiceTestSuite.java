package com.english.tests;

import com.english.Customer;
import com.english.CustomerService;
import com.english.DBHandler;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerServiceTestSuite {

    protected CustomerService customerService = CustomerService.getInstance();
    //TODO add setup()
    @Test
    public void testSaveDelete() {
        List<Customer> allCustomers = customerService.findAll(null);
        int numOfCustomers = allCustomers.size();
        Customer customer = new Customer("Jack", "Sparrow", "Carribean", 6, "skysparrow", true, "sparrow@gmail.com", "I'm the Pirate", "pass");
        customerService.save(customer);
        assertEquals(numOfCustomers + 1, customerService.findAll(null).size());

        Optional<Customer> result = DBHandler.getSingleCustomer(customer.getEmail());
        if (result.isPresent()) {
            customerService.delete(result.get());
            assertEquals(numOfCustomers, customerService.findAll(null).size());
        }
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer("Jack", "Sparrow", "Carribean", 6, "skysparrow", true, "sparrow@gmail.com", "I'm the Pirate", "pass");
        customerService.save(customer);

        String firstName = "Quentin";
        String lastName = "Tarantino";

        Optional<Customer> tempObject = DBHandler.getSingleCustomer(customer.getEmail());
        if (tempObject.isPresent()) {
            tempObject.get().setFirstName(firstName);
            tempObject.get().setLastName(lastName);
            customerService.update(tempObject.get());
        }

        Optional<Customer> result = DBHandler.getSingleCustomer(customer.getEmail());
        if(result.isPresent()){
            assertEquals(result.get().getFirstName(), firstName);
            assertEquals(result.get().getLastName(), lastName);
            customerService.delete(tempObject.get());
        }
    }
}