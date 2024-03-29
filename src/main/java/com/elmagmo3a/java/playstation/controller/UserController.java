
package com.elmagmo3a.java.playstation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elmagmo3a.java.playstation.jwt.JwtTokenUtil;
import com.elmagmo3a.java.playstation.model.CreateUserRequest;
import com.elmagmo3a.java.playstation.service.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity createUser(@Valid @RequestBody CreateUserRequest createUserRequest,
			@RequestHeader("token") String token, @RequestHeader("caller") String caller) {
		Long storeId = jwtTokenUtil.getStoreIdFromToken(token);
		return userService.createUser(createUserRequest, storeId, caller);
	}

}
