package com.elmagmo3a.java.playstation.jwt.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elmagmo3a.java.playstation.entity.User;
import com.elmagmo3a.java.playstation.jwt.JwtInMemoryUserDetailsService;
import com.elmagmo3a.java.playstation.jwt.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "http://localhost:8101")
public class JwtAuthenticationRestController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "${jwt.get.token.uri}")
	public ResponseEntity createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) {

		final User userDetails = jwtInMemoryUserDetailsService.loadEntityUserByUsernameAndPassword(
				authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "${jwt.refresh.token.uri}")
	public ResponseEntity refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

}
