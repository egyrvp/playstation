package com.elmagmo3a.java.playstation.util;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {

	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private CommonUtils() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Helper method to check object is null or not
	 * 
	 * @param        <T>
	 * @param object
	 * @return isNull boolean
	 */
	public static <T> boolean isNull(T object) {
		return object == null;
	}

	/**
	 * check if the assigned string is number.
	 * 
	 * @param String
	 * @return number or null
	 */
	public static Long tryParse(String text) {
		try {
			return Long.parseLong(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Utility method to check null or empty for passed object
	 *
	 * @param object to be checked
	 * @return true if passed object is null or empty
	 */
	public static boolean isBlankOrNull(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof String) {
			return ((String) object).isEmpty();
		}
		if (object instanceof Collection) {
			return ((Collection<?>) object).isEmpty();
		}
		if (object instanceof Map) {
			return ((Map<?, ?>) object).isEmpty();
		}
		if (object.getClass().isArray()) {
			return ((Object[]) object).length == 0;
		}
		return false;
	}

	/**
	 * Utility method to check that passed object is not null or empty
	 *
	 * @param object to be checked
	 * @return true if passed object is null or empty
	 */
	public static boolean isNotBlankOrNull(Object object) {
		return !isBlankOrNull(object);
	}

	public static String toJson(Object data) {
		
		ObjectMapper om = new ObjectMapper();
		if (data != null) {
			try {
				return om.writeValueAsString(data);
			} catch (JsonProcessingException e) {
				logger.error("Exception while mapping Object {} to String",data, e);
				e.printStackTrace();
			}
		}
		return null;
	}
	

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

}
