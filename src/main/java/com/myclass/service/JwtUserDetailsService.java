package com.myclass.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.config.JwtTokenUtil;
import com.myclass.config.StaticCons;
import com.myclass.entity.UserDTO;
import com.myclass.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private MailService mail;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.myclass.entity.UserDTO user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}

	public UserDTO save(UserDTO user) {
		UserDTO newUser = new UserDTO();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		newUser.setRoleId(StaticCons.ROLE_USER);
		newUser.setId(UUID.randomUUID().toString());
		newUser.setActive(false);
		String randomToken = UUID.randomUUID().toString();
		newUser.setActiveToken(randomToken);
//		mail.sendSimpleMessage(user.getEmail(), "Activate account", generateActiveToken(newUser.getId(), randomToken));
		mail.sendActiveMailTemplate(user.getEmail(),"Activate account", generateActiveToken(newUser.getId(), randomToken), user.getUsername());
		return userRepository.save(newUser);
	}

	private String generateActiveToken(String userId, String token) {
		String currentURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		String activeUrl = currentURL + "/active/" + jwtTokenUtil.generateActiveToken(token, userId);

		return activeUrl;
	}

	public UserDTO checkActiveToken(String userId, String activeToken) {

		return userRepository.searchUserWithToken(userId, activeToken);
	}
}
