package com.elmagmo3a.java.playstation.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Common {

	public static boolean validateUsernameMatchesEmailPattern(String userName) {

//		Pattern emailNamePtrn = Pattern
//				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Pattern emailNamePtrn = Pattern.compile(
				"(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
		Matcher mtch = emailNamePtrn.matcher(userName);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}

	public static boolean validateMobileNumberMatchesNumbers(String mobileNumber) {

		Pattern mobileNumberPattern = Pattern.compile("^[0-9]\\d{11}$|^[0-9]\\d{11}$");
		Matcher mtch = mobileNumberPattern.matcher(mobileNumber);
		if (mtch.matches()) {
			return true;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mobile number must be valid positive number only");
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object instance) {
		if (instance == null) {
			return true;
		} else if (instance instanceof Collection) {
			return ((Collection) instance).isEmpty();
		} else if (instance instanceof Map) {
			return ((Map) instance).isEmpty();
		} else if (instance.getClass().getName().endsWith("[")) {
			return Array.getLength(instance) == 0;
		} else if (instance instanceof CharSequence) {
			return instance.toString().trim().length() == 0;
		}
		return false;
	}
}
