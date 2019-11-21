
package com.elmagmo3a.java.playstation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elmagmo3a.java.playstation.jwt.JwtTokenUtil;
import com.elmagmo3a.java.playstation.model.CreateStoreRequest;
import com.elmagmo3a.java.playstation.service.StoreService;

@RestController
@RequestMapping("/store")
@Validated
public class StoreController {

	@Autowired
	StoreService storeService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity createStore(@Valid @RequestBody CreateStoreRequest createStoreRequest) {
		return storeService.createStore(createStoreRequest);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}/owner")
	public ResponseEntity setOwner(@RequestParam Long id, @RequestBody Long userId) {
		return storeService.setStoreOwner(id, userId);
	}

}
