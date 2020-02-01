package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);
	
	List<User> findByFullname(String fullname);
	
	User findByAddress(String address);
	
	@Query("SELECT u FROM User u WHERE u.fullname = :name OR u.email = :email")
	List<User> search(@Param("name")  String fullname, @Param("email") String email);
	
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.fullname, r.description) FROM User u JOIN u.role r")
	List<UserDto> getAllDTO();
}
