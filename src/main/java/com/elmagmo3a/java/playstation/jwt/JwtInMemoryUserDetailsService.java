package com.elmagmo3a.java.playstation.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.repository.UserRepository;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	public UserDetails loadEntityUserByUsernameAndPassword(String username, String password) {
		Optional<User> findFirst = userRepository.findByUsernameAndPassword(username, password);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return findFirst.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> findFirst = userRepository.findByUsername(username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return findFirst.get();
	}

}
