package com.myclass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.myclass.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{
	
	boolean existsByTitle(String title);
	boolean existsById(String id);
	Optional<Movie> findById(String id);
	@Query("SELECT e FROM Movie e ORDER BY e.title DESC")
	List<Movie> getAllMovie();

}
