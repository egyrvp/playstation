package com.elmagmo3a.java.playstation.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elmagmo3a.java.playstation.log.Log;
import com.elmagmo3a.java.playstation.log.LogTemplate;
import com.elmagmo3a.java.playstation.model.CreateUserRequest;
import com.elmagmo3a.java.playstation.repository.StoreRepository;

@Service
public class StoreService {

	@Log
	Logger logger;

	@Autowired
	private StoreRepository storeRepository;

	public ResponseEntity<Object> createStore(CreateUserRequest createUserRequest, Long storeId, String createdBy) {
		logger.info(LogTemplate.CREATE_X_START_WITH1, "User", "Request", createUserRequest);

		storeRepository.findAll();
		logger.info(LogTemplate.CREATE_X_END, "User");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
