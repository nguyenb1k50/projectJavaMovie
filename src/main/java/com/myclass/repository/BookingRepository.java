package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.entity.Booking;
import com.myclass.entity.Calendar;

import java.sql.Date;
import java.util.ArrayList;
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

	@Query(value =  "SELECT b.id, GROUP_CONCAT(s.seat_code SEPARATOR ', ') as seats, c.cinemar_name, m.title, s.show_time,cal.open_date, u.username,u.email,u.address,u.phone "
			+ "from bookings b, seats s, cinemas c, movies m, users u,calendars cal "
			+ "where b.id = s.booking_id "
			+ "and b.calendar_id = cal.id "
			+ "and cal.cinemar_id = c.id "
			+ "and cal.movie_id = m.id "
			+ "and b.user_id = u.id "
			+ "and b.id = :bookingId" , nativeQuery = true)
	List<String[]> getBookingDetail(@Param("bookingId") String bookingId);
}
