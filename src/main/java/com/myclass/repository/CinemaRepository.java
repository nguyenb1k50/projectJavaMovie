package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.entity.Calendar;
import com.myclass.entity.Cinemar;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CinemaRepository extends JpaRepository<Cinemar, String>{
	
	boolean existsByTitle(String title);
	
	@Query("SELECT c FROM Cinemar c ORDER BY c.title")
	List<Cinemar> getAllCinema();
	
	@Query("SELECT c FROM Cinemar c ORDER BY ((c.lat - :userLat)*(c.lat- :userLat)) + ((c.lng - :userLng)*(c.lng - :userLng)) ASC")
	List<Cinemar> getCinemaSortWithLocation(@Param("userLat") double userLat,@Param("userLng") double userLng);

}
