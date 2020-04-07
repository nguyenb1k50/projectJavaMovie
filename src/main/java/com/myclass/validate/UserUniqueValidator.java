package com.myclass.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.myclass.repository.UserRepository;

public class UserUniqueValidator implements
  ConstraintValidator<UniqueUserNameConstraint, String> {
	
	private UserRepository userRepository;
	 
    public UserUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void initialize(UniqueUserNameConstraint UniqueUserNameConstraint) {
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value!= null && userRepository.findByUsername(value) == null && userRepository.findByEmail(value) == null;
	}
 
}
