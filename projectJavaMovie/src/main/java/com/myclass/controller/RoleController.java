package com.myclass.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;

@RestController
@RequestMapping("api/role")
public class RoleController {

	@Autowired
	RoleRepository roleRepository;
	
	@PostMapping("")
	public Object post(@RequestBody Role role) {
		
		role.setId(UUID.randomUUID().toString());
		Role entity = roleRepository.save(role);
		
		return new ResponseEntity<Object>(entity, HttpStatus.CREATED);
	}
}
