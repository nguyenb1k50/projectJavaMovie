package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.entity.Booking;
import com.myclass.entity.Calendar;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String>{
	
	@Query("SELECT b FROM Booking b")
	List<Booking> getAllBooking();
	
	@Query("SELECT c FROM Calendar c where c.openDate = :date and c.movieId = :movieId and c.cinemarId = :cinemarId")
	Calendar checkExistCal(@Param("date") Date date,@Param("movieId") String movieId,@Param("cinemarId") String cinemarId);

}
