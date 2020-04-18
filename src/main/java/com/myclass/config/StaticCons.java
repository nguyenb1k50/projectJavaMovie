package com.myclass.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
	public static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
}
