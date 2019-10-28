package com.elmagmo3a.java.playstation.util;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {

	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private CommonUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Helper method to retrieve the messages from the localization file
	 * 
	 * @param key    key for the message in the file
	 * @param lang   locale to be retrieved e.g {en, ar. du}
	 * @param params one or more parameter to be combined with the retrieved message
	 *
	 * @return message as String
	 * 
	 **/
	public static String resourceBundle(String key, Locale lang, Object... params) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages", lang);
		return MessageFormat.format(resourceBundle.getString(key), params);
	}

	/**
	 * Helper method to retrieve the messages from the localization file
	 * 
	 * @param key  key for the message in the file
	 * @param lang locale to be retrieved e.g {en, ar. du}
	 *
	 * @return message as String
	 * 
	 **/
	public static String resourceBundle(String key, Locale lang) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages", lang);
		return resourceBundle.getString(key);
	}

	/**
	 * Helper method to retrieve the messages from the default locale (en)
	 * 
	 * @param key key for the message in the file
	 *
	 * @return message as String
	 * 
	 **/
	public static String resourceBundle(String key) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);
		return resourceBundle.getString(key);
	}

	/**
	 * Helper method to retrieve the messages from the default locale (en)
	 * 
	 * @param key    key for the message in the file
	 * @param params one or more parameter to be combined with the retrieved message
	 *
	 * @return message as String
	 * 
	 **/
	public static String resourceBundle(String key, Object... params) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);
		return MessageFormat.format(resourceBundle.getString(key), params);
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
}
