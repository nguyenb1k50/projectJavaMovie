package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.myclass.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{
	
	boolean existsByTitle(String title);
	boolean existsById(String id);
	@Query("SELECT e FROM Movie e ORDER BY e.title DESC")
	List<Movie> getAllMovie();
	
	@Query("SELECT e FROM Movie e WHERE e.openDate > CURDATE() ORDER BY e.title DESC")
	List<Movie> getComingMovie();

	@Query("SELECT e FROM Movie e WHERE e.openDate < CURDATE() AND e.closeDate > CURDATE() ORDER BY e.title DESC")
	List<Movie> getShowingMovie();
	
	@Query("SELECT e FROM Movie e WHERE e.closeDate < CURDATE() ORDER BY e.title DESC")
	List<Movie> getExpireMovie();
}
