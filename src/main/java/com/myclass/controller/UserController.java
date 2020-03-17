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
import com.myclass.entity.UserDTO;
import com.myclass.repository.UserRepository;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("")
	public Object get() {
		List<UserDTO> users = userRepository.findAll();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/getDto")
	public Object getDto() {
		List<UserDto> users = userRepository.getAllDTO();
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public Object get(@PathVariable String id) {
		UserDTO user = userRepository.findById(id).get();
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@GetMapping("/search/{email}/{username}")
	public Object getByEmail(@PathVariable String email, @PathVariable String username) {
		
		List<UserDTO> user = userRepository.search(username, email);
		
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@PostMapping("")
	public Object post(@RequestBody UserDTO user) {
		
		user.setId(UUID.randomUUID().toString());
		UserDTO entity = userRepository.save(user);
		
		return new ResponseEntity<UserDTO>(entity, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @RequestBody UserDTO user) {
		
		if(userRepository.existsById(id)) {
			user.setId(id);
			UserDTO entity = userRepository.save(user);
			return new ResponseEntity<UserDTO>(entity, HttpStatus.OK);	
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
