package com.english.tests;

import com.english.model.Customer;
import com.english.service.CustomerService;
import com.english.db.DBHandler;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CustomerServiceTestSuite {

    protected CustomerService customerService = CustomerService.getInstance();
    //TODO add setup()
    @Test
    public void testSaveDelete() {
        List<Customer> allCustomers = customerService.findAll(null);
        int numOfCustomers = allCustomers.size();
        Customer customer = new Customer("Jack", "skysparrow", "sparrow@gmail.com", 5, "pass");
        customerService.save(customer);
        assertEquals(numOfCustomers + 1, customerService.findAll(null).size());

        Optional<Customer> result = DBHandler.getSingleCustomerViaContactMe(customer.getContactMe());
        if (result.isPresent()) {
            customerService.delete(result.get());
            assertEquals(numOfCustomers, customerService.findAll(null).size());
        }
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer("Jack", "skysparrow", "sparrow@gmail.com", 5, "pass");
        customerService.save(customer);

        String name = "Quentin";
        String skype = "queueskype";

        Optional<Customer> tempObject = DBHandler.getSingleCustomerViaContactMe(customer.getContactMe());
        if (tempObject.isPresent()) {
            tempObject.get().setName(name);
            tempObject.get().setSkype(skype);
            customerService.update(tempObject.get());
        }

        Optional<Customer> result = DBHandler.getSingleCustomerViaContactMe(customer.getContactMe());
        if(result.isPresent()){
            assertEquals(result.get().getName(), name);
            assertEquals(result.get().getSkype(), skype);
            customerService.delete(tempObject.get());
        }
    }
}