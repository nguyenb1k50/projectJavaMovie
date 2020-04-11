package com.myclass.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.myclass.entity.JwtResponse;
import com.myclass.entity.UserDTO;
import com.myclass.repository.UserRepository;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

@Aspect
@Component
public class AspectClass {
	private UserRepository userRepository;
	private static final Map<String, List<String>> roleInheritance = new HashMap<String,List<String>>(){{
		put("admin", Arrays.asList("SUPER_ADMIN","ADMIN"));
		put("user", Arrays.asList("USER","SUPER_ADMIN","ADMIN"));
	}};
	
	public AspectClass(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Around(" @annotation(Authorized)")
	public Object validateAuthorized(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Authorized au = method.getAnnotation(Authorized.class);
		String role = au.role();
		// get current login user
		String currentUserName;
		UserDTO currentUser;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		} else {
			throw new AccessDeniedException("đăng nhâp đi bạn!");
		}
		if (currentUserName != null) {
			currentUser = userRepository.findByUsername(currentUserName);
			String currRole = currentUser.getRole().getName();
			if(currRole.equalsIgnoreCase(role) ||  roleInheritance.get(role).contains(currRole.toUpperCase())) {
				return pjp.proceed();
			}
			throw new AccessDeniedException("sai role nhe");
		}
		return pjp.proceed();
	}
}
