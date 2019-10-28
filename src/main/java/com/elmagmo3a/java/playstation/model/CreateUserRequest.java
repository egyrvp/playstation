package com.elmagmo3a.java.playstation.model;

import com.elmagmo3a.java.playstation.entity.User.Type;

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

	private String name;

	private String username;

	private String password;

	private Type type;

}
