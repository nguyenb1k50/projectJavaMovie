package com.myclass.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.myclass.service.JwtUserDetailsService;

import io.jsonwebtoken.Claims;

import com.myclass.config.JwtTokenUtil;
import com.myclass.entity.JwtRequest;
import com.myclass.entity.JwtResponse;
import com.myclass.entity.Movie;
import com.myclass.entity.UserDTO;
import com.myclass.entity.UserDTO.CreateGroup;
import com.myclass.repository.UserRepository;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody @Validated(CreateGroup.class) UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/active/{activeToken}", method = RequestMethod.GET)
	public ResponseEntity<?> saveUser(@PathVariable String activeToken) throws Exception {		
		Claims claim = jwtTokenUtil.getAllInfo(activeToken);
		String userId = claim.get("userId").toString();
		String token = claim.getSubject();
		UserDTO user = userDetailsService.checkActiveToken(userId, token);
		if(user != null) {
			user.setActive(true);
			user.setActiveToken("");
			userRepository.save(user);
			return new ResponseEntity<>("Your account has been actived!",HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Invalid request!",HttpStatus.BAD_REQUEST);
	}
}
