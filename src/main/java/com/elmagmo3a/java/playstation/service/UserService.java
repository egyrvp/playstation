package com.elmagmo3a.java.playstation.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.elmagmo3a.java.playstation.entity.Store;
import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.log.Log;
import com.elmagmo3a.java.playstation.log.LogTemplate;
import com.elmagmo3a.java.playstation.model.CreateUserRequest;
import com.elmagmo3a.java.playstation.repository.StoreRepository;
import com.elmagmo3a.java.playstation.repository.UserRepository;
import com.elmagmo3a.java.playstation.util.MessageKey;
import com.elmagmo3a.java.playstation.util.MessageResolver;
import com.elmagmo3a.java.playstation.validation.StoreValidator;
import com.elmagmo3a.java.playstation.validation.UserValidator;

@Service
@Validated
public class UserService {

	@Log
	Logger logger;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoreRepository storeRepository;

	public ResponseEntity<Object> createUser(CreateUserRequest createUserRequest, Long storeId, String createdBy) {
		logger.info(LogTemplate.CREATE_X_START_WITH1, "User", "Request", createUserRequest);

		logger.debug("validate store exists");
		Store store = storeRepository.findById(storeId).orElse(null);
		StoreValidator.checkActiveStoreExistence(store);

		logger.debug("validate username is unique");
		if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_EXIST, "Username"));
		}

		User user = new User();
		user.setName(createUserRequest.getName());
		user.setUsername(createUserRequest.getUsername());
		user.setPassword(createUserRequest.getPassword());
		user.setType(createUserRequest.getType());
		user.setMobile(createUserRequest.getMobile());
		user.setCreatedBy(createdBy);
		user.setStore(store);
		userRepository.save(user);
		logger.info(LogTemplate.CREATE_X_END, "User");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public User getUserById(@NotNull Long id) {
		logger.info(LogTemplate.GET_X_START_WITH, "User", id);
		User user = userRepository.findById(id).orElse(null);
		UserValidator.checkActiveUserExistence(user);
		logger.info(LogTemplate.GET_X_END_WITH, "User", id);
		return user;
	}

}
