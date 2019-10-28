
package com.elmagmo3a.java.playstation.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elmagmo3a.java.playstation.model.CreateUserRequest;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@PostMapping()
	public ResponseEntity createUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws SQLException {
		return null;
	}

}
