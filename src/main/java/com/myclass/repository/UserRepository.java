package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.UserDto;
import com.myclass.entity.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, String> {

	UserDTO findByEmail(String email);
	
	UserDTO findByUsername(String username);
	
	UserDTO findByAddress(String address);
	
	@Query("SELECT u FROM UserDTO u WHERE u.username = :name OR u.email = :email")
	List<UserDTO> search(@Param("name")  String username, @Param("email") String email);
	
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.username, r.description) FROM UserDTO u JOIN u.role r")
	List<UserDto> getAllDTO();
	
	@Query("SELECT u FROM UserDTO u WHERE u.id = :id AND u.activeToken = :token")
	UserDTO searchUserWithToken(@Param("id")  String userId, @Param("token") String activeToken);
}
