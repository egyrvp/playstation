package com.elmagmo3a.java.playstation.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.entity.User.Status;
import com.elmagmo3a.java.playstation.model.CreateUserRequest;
import com.elmagmo3a.java.playstation.util.CommonUtils;
import com.elmagmo3a.java.playstation.util.MessageKey;
import com.elmagmo3a.java.playstation.util.MessageResolver;

public interface UserValidator {

	public static void checkActiveUserExistence(User user) {

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_NOT_FOUND, "User"));
		}

		// Action is not authorized for an archived user
		if (user.getArchived()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_ARCHIVED, "User"));
		}

		// Action is not authorized for an archived user's company
		if (!Status.ACTIVE.equals(user.getStatus())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_INACTIVE, "User"));
		}

	}

	public static void validateCreateUserRequest(CreateUserRequest createUserRequest) {
		if (!CommonUtils.validateUsernameMatchesEmailPattern(createUserRequest.getUsername())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_INACTIVE, "User"));
		}
	}

}
