package com.myclass.repository;

import org.springframework.stereotype.Repository;

import com.myclass.dto.excelDTO;
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
			+ "and b.id = :bookingId "
			+ "group by s.show_time" , nativeQuery = true)
	List<String[]> getBookingDetail(@Param("bookingId") String bookingId);
	
	@Query(value =  "SELECT b.id, GROUP_CONCAT(s.seat_code SEPARATOR ', ') as seats, c.cinemar_name, m.title, s.show_time,cal.open_date, u.username,u.email,u.address,u.phone "
			+ "from bookings b, seats s, cinemas c, movies m, users u,calendars cal "
			+ "where b.id = s.booking_id "
			+ "and b.calendar_id = cal.id "
			+ "and cal.cinemar_id = c.id "
			+ "and cal.movie_id = m.id "
			+ "and b.user_id = u.id "
			+ "group by s.show_time, b.id" , nativeQuery = true)
	List<String[]> getBookingList();
	
	@Query(value =  "SELECT m.title, count(s.id) "
			+ "from bookings b, seats s, movies m, calendars c "
			+ "where s.booking_id = b.id "
			+ "and c.movie_id = m.id "
			+ "and b.calendar_id = c.id "
			+ "and year(b.create_date) = year(curdate()) "
			+ "and month(b.create_date) = month(curdate()) "
			+ "group by m.id "
			+ "order by count(s.id) desc "
			+ "limit 3" , nativeQuery = true)
	List<String[]> getListHotestMovie();
	
	@Query(value =  "select '08:00 - 16:00', count(s.id) as a\r\n" + 
			"from seats s\r\n" + 
			"where '08:00:00' <= s.show_time and s.show_time <= '15:59:00'\r\n" + 
			"union\r\n" + 
			"select '16:00 - 24:00',count(s.id) as a\r\n" + 
			"from seats s\r\n" + 
			"where '16:00:00' <= s.show_time and s.show_time <= '23:59:00'\r\n" + 
			"union\r\n" + 
			"select '24:00 - 08:00',count(s.id) as a\r\n" + 
			"from seats s\r\n" + 
			"where '00:00:00' <= s.show_time and s.show_time <= '07:59:00'\r\n" + 
			"order by a desc" , nativeQuery = true)
	List<String[]> getMostViewTimeFrame();
}
