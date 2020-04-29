package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.entity.Calendar;
import com.myclass.entity.Ratting;
import com.myclass.entity.Seat;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String>{
	@Query("SELECT s.seatCode FROM Seat s,Booking b, Calendar c "
			+ "where s.bookingId = b.id "
			+ "and b.calendarId = c.id "
			+ "and c.id = :calendarId "
			+ "and s.showTime = :showTime")
	 ArrayList<String> getAlreadyBookingSeat(@Param("calendarId") String calendarId, @Param("showTime") Time showTime);
}
