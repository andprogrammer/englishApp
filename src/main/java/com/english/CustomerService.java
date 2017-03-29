package com.english;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class CustomerService {

	private static CustomerService instance;
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
	
	private final HashMap<Long, Customer> contacts = new HashMap<>();
	private long nextId = 0;
	
	private CustomerService() {
	}
	
	public static CustomerService getInstance() {
		if(instance == null) {
			instance = new CustomerService();
			instance.ensureTestData();
		}
		return instance;
	}
	
	public synchronized List<Customer> findAll() {
		return findAll(null);
	}

	public synchronized List<Customer> findAll(String filter) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for(Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (filter == null || filter.isEmpty())
						|| contact.toString().toLowerCase().contains(filter.toLowerCase());
				if(passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch(CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {
			
			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized List<Customer> findAll(String filter, int start, int maxResults) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for(Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (filter == null || filter.isEmpty())
						|| contact.toString().toLowerCase().contains(filter.toLowerCase());
				if(passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch(CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {
			
			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});
		int end = start + maxResults;
		if(end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}
	
	public synchronized long count() {
		return contacts.size();
	}
	
	public synchronized void delete(Customer value) {
		contacts.remove(value.getId());
	}
	
	public synchronized void save(Customer entry) {
		if(entry == null) {
			LOGGER.log(Level.SEVERE, "Customer is null.");
			return;
		}
		if(entry.getId() == null) {
			entry.setId(++nextId);
		}
		try {
			entry = (Customer) entry.clone();
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}
	
	public void ensureTestData() {
		if(findAll().isEmpty()) {
			final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
					"Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
					"Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
					"Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
					"Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
					"Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
					"Jaydan Jackson", "Bernard Nilsen" };
			for(String name : names) {
				String[] split = name.split(" ");
				Customer newCustomer = new Customer();
				newCustomer.setFirstName(split[0]);
				newCustomer.setLastName(split[1]);
				newCustomer.setEmail(split[0].toLowerCase() + "@" + split[1].toLowerCase());
				newCustomer.setBirthDate(new Date());
				save(newCustomer);
			}
		}
	}
}
