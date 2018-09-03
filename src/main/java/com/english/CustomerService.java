package com.english;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerService {

	public enum FILTER_TYPE {
		FIRST_NAME,
		LAST_NAME,
		COUNTRY,
		ENGLISH_LEVEL,
		SKYPE,
		GENDER,
		EMAIL
	}
	
	private static CustomerService instance;
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

	private final ArrayList<Customer> contacts = new ArrayList<>();

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

	public synchronized List<Customer> findBy(String inputText, FILTER_TYPE inputTextType) {
		ArrayList<Customer> filteredCustomers = new ArrayList<>();
		for(Customer contact : contacts) {
			filterCustomers(inputText, inputTextType, filteredCustomers, contact);
		}
		Collections.sort(filteredCustomers, (o1, o2) -> o2.getId() - o1.getId());
		return filteredCustomers;
	}

	private void filterCustomers(String inputText, FILTER_TYPE inputTextType, ArrayList<Customer> filteredCustomers, Customer contact) {
		boolean passesFilter = (inputText == null || inputText.isEmpty());
		String contactData = new String();
		switch(inputTextType)
		{
		case FIRST_NAME: {
			contactData = contact.getFirstName();
			break;
		}
		case LAST_NAME: {
			contactData = contact.getLastName();
			break;
		}
		case COUNTRY: {
			contactData = contact.getCountry();
			break;
		}
		case ENGLISH_LEVEL: {
			contactData = Integer.toString(contact.getEnglishLevel());
			break;
		}
		case SKYPE: {
			contactData = contact.getSkype();
			break;
		}
		case GENDER: {
			passesFilter = contact.getGender().name().toLowerCase().equals(inputText.toLowerCase());
			break;
		}
		case EMAIL: {
			contactData = contact.getEmail();
			break;
		}
		default:
			break;
		}
		
		passesFilter = passesFilter || contactData.toLowerCase().contains(inputText.toLowerCase());
		if(passesFilter) {
			filteredCustomers.add(contact);
		}
	}

	public synchronized List<Customer> findAll(String filter) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		filterContacts(filter, arrayList);
		Collections.sort(arrayList, (o1, o2) -> o2.getId() - o1.getId());
		return arrayList;
	}
	
	public synchronized List<Customer> findAll(String filter, int start, int maxResults) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		filterContacts(filter, arrayList);
		Collections.sort(arrayList, Comparator.comparingInt(Customer::getId));
		int end = start + maxResults;
		if(end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	private void filterContacts(String filter, ArrayList<Customer> arrayList) {
		for(Customer contact : contacts) {
			boolean passesFilter = (filter == null || filter.isEmpty())
					|| contact.toString().toLowerCase().contains(filter.toLowerCase());
			if(passesFilter) {
				arrayList.add(contact);
			}
		}
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
		DBHandler.AddNewCustomerToDB(entry);
		contacts.add(entry);
	}
	
	public void ensureTestData() {
	    for(Customer customer : DBHandler.GetCustomerFromDB()) {
            contacts.add(customer);
        }
	}

	public void updateCustomersFromDB() {
		contacts.clear();
		ensureTestData();
	}

	public synchronized void update(Customer customer) {
		if (customer == null) {
			LOGGER.log(Level.SEVERE, "Customer is null.");
			return;
		}
		DBHandler.UpdateCustomerInDB(customer);
	}
}
