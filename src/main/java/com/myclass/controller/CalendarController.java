package com.myclass.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.entity.Calendar;
import com.myclass.entity.Cinemar;
import com.myclass.entity.Movie;
import com.myclass.repository.CalendarRepository;

@RestController
@RequestMapping("api/calendar")
public class CalendarController {

	@Autowired
	CalendarRepository calendarRepository;
	
	@GetMapping("")
	public Object get(@RequestParam(required = false ,defaultValue = "0") double userLat, 
			@RequestParam(required = false, defaultValue = "0") double userLng ) {
		List<Calendar> calenders;
		if(userLat != 0.0 && userLng != 0.0) {
			calenders = calendarRepository.getCalendarwithLocation(userLat, userLng);
		}else {
			calenders = calendarRepository.getAllCalendar();
		}
		//List<Calendar> calenders = calendarRepository.getAllCalendar();
		for(Calendar c : calenders)
		{
			Movie m = c.getMovie();
			Cinemar cine = c.getCinemar();
			String mName = m.getTitle();
			String cName = cine.getTitle();
			c.setMovieName(mName);
			c.setCinemaName(cName);
			c.setMovieDuration(m.getDuration());
			
		}
		return new ResponseEntity<List<Calendar>>(calenders, HttpStatus.OK);
	}
	
	@PostMapping()
	public Object post(@RequestBody Calendar calender) {
		Calendar cal = calendarRepository.checkExistCal(calender.getOpenDate(), calender.getMovieId(), calender.getCinemarId());
		if(cal != null) {
			Iterator<Time> listTime = cal.getTime().iterator();
			while (listTime.hasNext()) {
				int movieDuration = (cal.getMovie().getDuration()+15) * 60000;
				Date movieOpen = cal.getMovie().getOpenDate();
				Date movieClose = cal.getMovie().getCloseDate();
				Long from = listTime.next().getTime();				
				Long end = from +movieDuration;
				Iterator<Time> listNewTime = calender.getTime().iterator();
				while (listNewTime.hasNext()) {
					Long newTime = listNewTime.next().getTime();
					//check new time
					if(newTime >= from && newTime <= end || newTime+movieDuration >= from && newTime+movieDuration <= end) {
						return new ResponseEntity<String>("Existed show time!!!", HttpStatus.BAD_REQUEST);
					}
					if(calender.getOpenDate().before(movieOpen)  || calender.getOpenDate().after(movieClose)) {
						return new ResponseEntity<String>("Date invalid!!!", HttpStatus.BAD_REQUEST);
					}
				}
			}
			ArrayList<Time> t = cal.getTime();
			t.addAll(calender.getTime());
			cal.setTime(t);
			Calendar c = calendarRepository.save(cal);
			return new ResponseEntity<Calendar>(c, HttpStatus.CREATED);
		}else {
			calender.setId(UUID.randomUUID().toString());
			Calendar c = calendarRepository.save(calender);
			return new ResponseEntity<Calendar>(c, HttpStatus.CREATED);
		}
		
	}
	
//	@PutMapping("/{id}")
//	public Object post(@PathVariable String id, @RequestBody Catagory catagory) {
//		
//		if(catagoryRepository.existsById(id)) {
//			catagory.setId(id);
//			Catagory entity = catagoryRepository.save(catagory);
//			return new ResponseEntity<Catagory>(entity, HttpStatus.OK);	
//		}
//		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
//	}
//	
//	
//	@DeleteMapping("/{id}")
//	public Object delete(@PathVariable String id) {
//		
//		if(catagoryRepository.existsById(id)) {
//			catagoryRepository.deleteById(id);
//			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
//		}
//		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
//	}
}
