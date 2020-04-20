package com.myclass.controller;

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
import com.myclass.entity.Catagory;
import com.myclass.entity.Cinemar;
import com.myclass.repository.CalendarRepository;
import com.myclass.repository.CinemaRepository;

@RestController
@RequestMapping("api/cinema")
public class CinemaController {

	@Autowired
	CinemaRepository cinemaRepository;
	
	@GetMapping("")
	public Object get(@RequestParam(required = false ,defaultValue = "0") double userLat, 
			@RequestParam(required = false, defaultValue = "0") double userLng ) {
		List<Cinemar> c;
		if(userLat != 0.0 && userLng != 0.0) {
			c = cinemaRepository.getCinemaSortWithLocation(userLat, userLng); 
		}else {
			c = cinemaRepository.getAllCinema();
		}
		
		return new ResponseEntity<List<Cinemar>>(c, HttpStatus.OK);
	}
	
	@PostMapping()
	public Object post(@RequestBody Cinemar cinema) {
		Boolean p = cinemaRepository.existsByTitle(cinema.getTitle());
		if(!p) {
			cinema.setId(UUID.randomUUID().toString());
			Cinemar c = cinemaRepository.save(cinema);
			return new ResponseEntity<Cinemar>(c, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Cinema exist",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public Object post(@PathVariable String id, @RequestBody Cinemar cinema) {		
		if(cinemaRepository.existsById(id)) {
			cinema.setId(id);
			Cinemar c = cinemaRepository.save(cinema);
			return new ResponseEntity<Cinemar>(c, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}	
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		
		if(cinemaRepository.existsById(id)) {
			cinemaRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
	}
}
