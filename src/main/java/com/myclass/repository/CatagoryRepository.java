package com.myclass.repository;

import org.springframework.stereotype.Repository;
import com.myclass.entity.Catagory;
import com.myclass.entity.UserDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CatagoryRepository extends JpaRepository<Catagory, String>{
	
	Optional<Catagory> findById(String id);
	boolean existsByTitle(String title);
	boolean existsById(String id);
	@Query("SELECT e FROM Catagory e ORDER BY e.title DESC")
	List<Catagory> getAllCatagory();

}
