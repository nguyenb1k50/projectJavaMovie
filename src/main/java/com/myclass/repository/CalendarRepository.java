package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.entity.Calendar;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, String>{
	
	@Query("SELECT c FROM Calendar c where c.openDate >= CURDATE()")
	List<Calendar> getAllCalendar();
	
	@Query("SELECT c FROM Calendar c "
			+ "left join Cinemar cine on cine.id = c.cinemarId \r\n" + 
			"where c.openDate >= CURDATE()\r\n" + 
			"ORDER BY ((cine.lat - :userLat)*(cine.lat- :userLat)) + ((cine.lng - :userLng)*(cine.lng - :userLng)) asc")
	List<Calendar> getCalendarwithLocation(@Param("userLat") double userLat,@Param("userLng") double userLng);
	
	@Query("SELECT c FROM Calendar c where c.openDate = :date and c.movieId = :movieId and c.cinemarId = :cinemarId")
	Calendar checkExistCal(@Param("date") Date date,@Param("movieId") String movieId,@Param("cinemarId") String cinemarId);

}
