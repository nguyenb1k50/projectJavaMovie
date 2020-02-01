package com.myclass.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("")
	public Object get() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/getDto")
	public Object getDto() {
		List<UserDto> users = userRepository.getAllDTO();
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public Object get(@PathVariable String id) {
		User user = userRepository.findById(id).get();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/search/{email}/{fullname}")
	public Object getByEmail(@PathVariable String email, @PathVariable String fullname) {
		
		List<User> user = userRepository.search(fullname, email);
		
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@PostMapping("")
	public Object post(@RequestBody User user) {
		
		user.setId(UUID.randomUUID().toString());
		User entity = userRepository.save(user);
		
		return new ResponseEntity<User>(entity, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @RequestBody User user) {
		
		if(userRepository.existsById(id)) {
			user.setId(id);
			User entity = userRepository.save(user);
			return new ResponseEntity<User>(entity, HttpStatus.OK);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
}
