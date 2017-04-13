package com.english;

public class GlobalFunctions {

	public static boolean isValidEmailAddress(String email) {
		String regularExpression = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile(regularExpression);
		java.util.regex.Matcher emailMatcher = regexPattern.matcher(email);
		return emailMatcher.matches();
	}
}
