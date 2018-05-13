package com.english;

import com.english.Customer.Sex;


public class GlobalFunctions {

	public static boolean isEmailAddressValid(String email) {
		String regularExpression = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile(regularExpression);
		java.util.regex.Matcher emailMatcher = regexPattern.matcher(email);
		return emailMatcher.matches();
	}
	
	public static Sex convertBooleanToSex(boolean entry) {
		return entry ? Sex.MALE : Sex.FEMALE;
	}
	
	public static boolean convertSexToBoolean(Sex entry) {
		return entry == Sex.MALE ? true : false;
	}

	public static String convertBooleanToString(boolean entry) { return entry ? "MALE" : "FEMALE"; }
}
