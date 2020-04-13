package com.myclass.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.myclass.entity.UserDTO;
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
		UserDTO user = null;
		if(value == null) {
			return true;
		}
		switch (fieldName) {
		case "username":
			user = userRepository.findByUsername(value);
			break;
		case "email":
			user = userRepository.findByEmail(value);
			break;
		default:
			break;
		}
		return user == null;
	}
 
}
