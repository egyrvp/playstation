package com.elmagmo3a.java.playstation.util;

public class MessageKey {

	private MessageKey() {
		throw new IllegalStateException("Utility class");
	}
	public static final String VALIDATION_OBJECT_NOT_FOUND = "validation.not.found";
	public static final String VALIDATION_OBJECT_ARCHIVED = "validation.archived";
	public static final String VALIDATION_OBJECT_INACTIVE = "valdiation.inactive";

	
	public static final String VALIDATION_NAME_NULL_FIELD_MESSAGE = "Name is mandatory";
	public static final String VALIDATION_USERNAME_NULL_FIELD_MESSAGE = "Username is mandatory";
	public static final String VALIDATION_PASS_WORD_NULL_FIELD_MESSAGE = "Password is mandatory";
	public static final String VALIDATION_TYPE_NULL_FIELD_MESSAGE = "TYPE is mandatory";
	public static final String VALIDATION_MOBILE_NULL_FIELD_MESSAGE = "Mobile is mandatory";


}
