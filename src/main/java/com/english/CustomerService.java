package com.english;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerService {

	public enum FILTER_TYPE {
		FIRST_NAME,
		LAST_NAME,
		COUNTRY,
		ENGLISH_LEVEL,
		SKYPE,
		SEX,
		EMAIL
	}
	
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

	public synchronized List<Customer> findBy(String inputText, FILTER_TYPE inputTextType) {
		ArrayList<Customer> filteredCustomers = new ArrayList<>();
		for(Customer contact : contacts.values()) {
			try {
				filterCustomers(inputText, inputTextType, filteredCustomers, contact);
			} catch(CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, "Cant clone customer in findByFirstName()", ex);
			}
		}
		Collections.sort(filteredCustomers, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return filteredCustomers;
	}

	private void filterCustomers(String inputText, FILTER_TYPE inputTextType, ArrayList<Customer> filteredCustomers,
			Customer contact) throws CloneNotSupportedException {
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
		case SEX: {
			passesFilter = contact.getSex().name().toLowerCase().equals(inputText.toLowerCase());
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
			filteredCustomers.add(contact.clone());
		}
	}
	
	public synchronized List<Customer> findAll(String filter) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		filterContacts(filter, arrayList);
		Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
		return arrayList;
	}
	
	public synchronized List<Customer> findAll(String filter, int start, int maxResults) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		filterContacts(filter, arrayList);
		Collections.sort(arrayList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
		int end = start + maxResults;
		if(end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	private void filterContacts(String filter, ArrayList<Customer> arrayList) {
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
			entry = entry.clone();
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
				newCustomer.setEnglishLevel(ThreadLocalRandom.current().nextInt(1,6));
				newCustomer.setSex(GlobalFunctions.convertBooleanToSex(ThreadLocalRandom.current().nextBoolean()));
				save(newCustomer);
			}
		}
	}
}
