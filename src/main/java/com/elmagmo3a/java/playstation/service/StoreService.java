package com.elmagmo3a.java.playstation.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elmagmo3a.java.playstation.entity.Store;
import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.log.Log;
import com.elmagmo3a.java.playstation.log.LogTemplate;
import com.elmagmo3a.java.playstation.model.CreateStoreRequest;
import com.elmagmo3a.java.playstation.repository.StoreRepository;
import com.elmagmo3a.java.playstation.validation.StoreValidator;

@Service
public class StoreService {

	@Log
	Logger logger;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private UserService userService;

	public ResponseEntity<Object> createStore(CreateStoreRequest createStoreRequest) {
		logger.info(LogTemplate.CREATE_X_START_WITH1, "Store", "Request", createStoreRequest);

		Store store = new Store();
		store.setAddress(createStoreRequest.getAddress());
		store.setMobile(createStoreRequest.getMobile());
		store.setName(createStoreRequest.getName());
		storeRepository.save(store);
		logger.info(LogTemplate.CREATE_X_END, "Store");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<Object> setStoreOwner(Long storeId, @NotNull Long userId) {
		logger.info("Start set store with id: {} owner to user with id: {}", storeId, userId);

		logger.debug("validate store exists");
		Store store = storeRepository.findById(storeId).orElse(null);
		StoreValidator.checkActiveStoreExistence(store);
		User user = userService.getUserById(userId);
		
		store.setOwner(user);
		storeRepository.save(store);

		logger.info("End Set store with id: {} owner to user with id: {}", storeId, user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
