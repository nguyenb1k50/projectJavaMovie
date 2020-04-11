package com.myclass.customException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errorMessageMap = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(e -> {
			if (e instanceof FieldError) {
				errorMessageMap.put(((FieldError) e).getField(), e.getDefaultMessage());
			}			
		});
		ErrorObject errorObject = new ErrorObject();
		errorObject.setMessages(errorMessageMap);

		return new ResponseEntity<>(errorObject,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		Map<String, String> errorMessageMap = new HashMap<>();
		errorMessageMap.put("status", "401");
		errorMessageMap.put("error", "unauthorized");
		errorMessageMap.put("message", ex.getMessage());
		
        return new ResponseEntity<>(errorMessageMap, HttpStatus.UNAUTHORIZED);
    }
}
