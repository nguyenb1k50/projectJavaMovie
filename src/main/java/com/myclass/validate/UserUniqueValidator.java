package com.myclass.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.myclass.repository.UserRepository;

public class UserUniqueValidator implements
  ConstraintValidator<CustomUniqueConstraint, String> {
	
	private UserRepository userRepository;
	private String fieldName;
	 
    public UserUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void initialize(CustomUniqueConstraint uniqueConstraint) {
    	fieldName = uniqueConstraint.fieldName();
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value!= null && userRepository.findByUsername(value) == null && userRepository.findByEmail(value) == null;
	}
 
}
