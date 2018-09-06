package com.english;

import com.english.Customer.Gender;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class GlobalFunctions {

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
	
	public static Customer.Gender convertBooleanToGender(boolean entry) {
		return entry ? Gender.MALE : Gender.FEMALE;
	}
	
	public static boolean convertGenderToBoolean(Customer.Gender entry) {
		return entry == Gender.MALE ? true : false;
	}

	public static String convertBooleanToString(boolean entry) { return entry ? "MALE" : "FEMALE"; }
}
