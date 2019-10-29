package com.elmagmo3a.java.playstation.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageResolver {
	private static String errorMessage = "Exception while mapping JSON to Object";
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageResolver.class);

	private MessageResolver() {
		throw new IllegalStateException("Utility class");

	}

	/**
	 * 
	 * @param string
	 * @return String after erasing the space
	 */
	public static String eraseSpaces(String str) {
		if (str == null)
			return null;
		if (str.equals("null") || str.equals("NULL") || str.equals("Null"))
			return "";
		int idx = -1;
		idx = str.indexOf(' ');
		while (idx != -1) {
			str = str.substring(0, idx) + str.substring(idx + 1, str.length());
			idx = str.indexOf(' ');
		}
		idx = str.indexOf('\t');
		while (idx != -1) {
			str = str.substring(0, idx) + str.substring(idx + 1, str.length());
			idx = str.indexOf('\t');
		}
		idx = str.indexOf('\r');
		while (idx != -1) {
			str = str.substring(0, idx) + str.substring(idx + 1, str.length());
			idx = str.indexOf('\r');
		}
		idx = str.indexOf('\n');
		while (idx != -1) {
			str = str.substring(0, idx) + str.substring(idx + 1, str.length());
			idx = str.indexOf('\n');
		}
		return str;
	}

	public static String serialize(Object obj) {
		ObjectMapper om = new ObjectMapper();
		if (obj != null) {
			try {
				return om.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				LOGGER.error("error happened", e);
			}
		}
		return null;
	}

	public static <O> O deSerialize(String str, Class<O> valueType) {
		ObjectMapper om = new ObjectMapper();
		if (str != null && !str.equals("")) {
			try {
				om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return om.readValue(str, valueType);
			} catch (IOException e) {
				LOGGER.error(errorMessage, e);
			}
		}
		return null;
	}

	public static String deleteBackslash(String url) {
		if (url.contains("/"))
			url = url.replace("/", "");
		return url;
	}

	public static <T> boolean isNull(T object) {
		return object == null;
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
}
