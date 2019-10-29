package com.elmagmo3a.java.playstation.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	private String username;
	@NotBlank(message = MessageKey.VALIDATION_PASS_WORD_NULL_FIELD_MESSAGE)
	private String password;
	@NotNull(message = MessageKey.VALIDATION_TYPE_NULL_FIELD_MESSAGE)
	private Type type;

}
