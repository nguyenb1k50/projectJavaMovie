package com.myclass.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.entity.UserDTO;
import com.myclass.repository.UserRepository;

public class StaticCons {
	public static final String ROLE_ADMIN = "1";
	public static final String ROLE_USER = "2";
	
	private static String UPLOAD_DIR = "src/main/resources/static/upload";
	@Autowired
	static UserRepository userRepository;
	
	public static UserDTO currentUser(String username) {
		UserDTO user = userRepository.findByUsername(username);
		return user;
	}
	
	public static String currentUrl() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()+"/";
	}
	
	public static String saveImage(MultipartFile img) {
		try {
			// Get the file and save it somewhere
			byte[] bytes = img.getBytes();
			// File.separator for difference OS platforms
			Path path = Paths.get(System.getProperty("user.dir") + File.separator + UPLOAD_DIR + File.separator
					+ img.getOriginalFilename());
			if (Files.write(path, bytes) != null) {
				return ("upload/" + img.getOriginalFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
