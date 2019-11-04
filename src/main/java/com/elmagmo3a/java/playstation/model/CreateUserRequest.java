package com.elmagmo3a.java.playstation.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.elmagmo3a.java.playstation.entity.User.Type;
import com.elmagmo3a.java.playstation.util.MessageKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class CreateUserRequest {

	@NotBlank(message = MessageKey.VALIDATION_NAME_NULL_FIELD_MESSAGE)
	private String name;
	@NotBlank(message = MessageKey.VALIDATION_USERNAME_NULL_FIELD_MESSAGE)
	@Pattern(regexp = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = MessageKey.VALIDATION_USERNAME_INVALID_FIELD_MESSAGE)
	private String username;
	@NotBlank(message = MessageKey.VALIDATION_PASS_WORD_NULL_FIELD_MESSAGE)
	private String password;
	@NotNull(message = MessageKey.VALIDATION_TYPE_NULL_FIELD_MESSAGE)
	private Type type;
	@NotNull(message = MessageKey.VALIDATION_TYPE_NULL_FIELD_MESSAGE)
	@Pattern(regexp = "^[0-9]\\d{11}$|^[0-9]\\d{11}$", message = MessageKey.VALIDATION_MOBILE_INVALID_FIELD_MESSAGE)
	private String mobile;

}
