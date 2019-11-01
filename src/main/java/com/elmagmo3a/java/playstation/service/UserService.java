package com.elmagmo3a.java.playstation.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.log.Log;
import com.elmagmo3a.java.playstation.log.LogTemplate;
import com.elmagmo3a.java.playstation.model.CreateUserRequest;
import com.elmagmo3a.java.playstation.repository.UserRepository;

@Service
public class UserService {

	@Log
	Logger logger;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity createUser(CreateUserRequest createUserRequest) {
		logger.info(LogTemplate.CREATE_X_START_WITH1, "User", "Request", createUserRequest);

		User user = new User();
		user.setName(createUserRequest.getName());
		user.setUsername(createUserRequest.getUsername());
		user.setPassword(createUserRequest.getPassword());
		user.setType(createUserRequest.getType());
		user.setMobile(createUserRequest.getMobile());
		userRepository.save(user);
		logger.info(LogTemplate.CREATE_X_END, "User");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
