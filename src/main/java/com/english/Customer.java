package com.english;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "customer")
public class Customer implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public Customer() {
    }

	public Customer(String name, String skype, String contactMe, int englishLevel, String password) {
	    this.name = name;
		this.skype = skype;
	    this.contactMe = contactMe;
		this.englishLevel = englishLevel;
	    this.password = password;
    }

	@Id
	@Column(name="id")
	private int id;

	@Column(name="customer_name")
	private String name="";

	@Column(name="customer_skype")
	private String skype="";

	@Column(name="customer_contact_me")
	private String contactMe="";

	@Column(name="customer_english_level")
	private int englishLevel=0;

	@Column(name="customer_password")
	private String password="";

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getContactMe() {
		return contactMe;
	}

	public void setContactMe(String contactMe) {
		this.contactMe = contactMe;
	}

	public int getEnglishLevel() {
		return englishLevel;
	}

	public void setEnglishLevel(int englishLevel) {
		this.englishLevel = englishLevel;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Customer)) return false;
		Customer customer = (Customer) o;
		return id == customer.id &&
				englishLevel == customer.englishLevel &&
				Objects.equals(name, customer.name) &&
				Objects.equals(skype, customer.skype) &&
				Objects.equals(contactMe, customer.contactMe) &&
				Objects.equals(password, customer.password);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, name, skype, contactMe, englishLevel, password);
	}
}
