package com.english;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class Customer implements Serializable, Cloneable {

	public enum Sex {
		FEMALE,
		MALE
	}
	
	private Long id;
	private String firstName="";
	private String lastName="";
	private Date birthDate;
	//private CustomerStatus status;
	private String country="";
	private String city="";
	private int englishLevel=0;
	private String skype="";
	private Sex sex=Sex.FEMALE;
	private String email="";
	private String description="";
	private String password="";
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/*public CustomerStatus getCustomerStatus() {
		return status;
	}
	
	public void setCustomerStatus(CustomerStatus status) {
		this.status = status;
	}*/
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getEnglishLevel() {
		return englishLevel;
	}
	
	public void setEnglishLevel(int englishLevel) {
		this.englishLevel = englishLevel;
	}
	
	public String getSkype() {
		return skype;
	}
	
	public void setSkype(String skype) {
		this.skype = skype;
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getpassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPersisted() {
		return id != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}
		if (obj instanceof Customer && obj.getClass().equals(getClass())) {
			return this.id.equals(((Customer) obj).id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}
	
	@Override
	public Customer clone() throws CloneNotSupportedException {
		return (Customer) super.clone();
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " " + country + " " + englishLevel + " " + skype + " " + sex + " " + email;
	}
}
