package com.myclass.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.myclass.entity.UserDTO;
import com.myclass.repository.UserRepository;

public class StaticCons {
	public static final String ROLE_ADMIN = "1";
	public static final String ROLE_USER = "2";
	@Autowired
	static UserRepository userRepository;
	
	public static UserDTO currentUser(String username) {
		UserDTO user = userRepository.findByUsername(username);
		return user;
	}
}
