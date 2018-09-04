package com.english;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "customer")
public class Customer implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public Customer() {
    }

	public Customer(String firstName, String lastName, String country, int englishLevel, String skype, Boolean gender, String email, String description, String password) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.country = country;
	    this.englishLevel = englishLevel;
	    this.skype = skype;
	    this.gender = GlobalFunctions.convertBooleanToGender(gender);
	    this.email = email;
	    this.description = description;
	    this.password = password;
    }

	public enum Gender {
		FEMALE,
		MALE
	}

	@Id
	@Column(name="id")
	private int id;

	@Column(name="customer_first_name")
	private String firstName="";

	@Column(name="customer_last_name")
	private String lastName="";

	@Column(name="customer_birth_date")
	private Date birthDate = new Date();

	//private CustomerStatus status;

	@Column(name="customer_country")
	private String country="";

	@Column(name="customer_city")
	private String city="";

	@Column(name="customer_english_level")
	private int englishLevel=0;

	@Column(name="customer_skype")
	private String skype="";

	@Column(name="customer_gender")
	private Gender gender =Gender.FEMALE;

	@Column(name="customer_email")
	private String email="";

	@Column(name="customer_description")
	private String description="";

	@Column(name="customer_password")
	private String password="";

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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
}
