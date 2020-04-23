package com.myclass.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.config.StaticCons;
import com.myclass.entity.Booking;
import com.myclass.entity.Catagory;
import com.myclass.entity.Seat;
import com.myclass.repository.BookingRepository;
import com.myclass.repository.CatagoryRepository;
import com.myclass.repository.SeatRepository;

@RestController
@RequestMapping("api/booking")
public class BookingController {

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	SeatRepository seatRepository;
	@Autowired
	PaymentController pay;

	@GetMapping("")
	public Object get() {
		List<Booking> b = bookingRepository.getAllBooking();
		return new ResponseEntity<List<Booking>>(b, HttpStatus.OK);
	}

	@GetMapping("/seats/{calendarId}")
	public Object get(@PathVariable String calendarId) {
		List<String> b = seatRepository.getAlreadyBookingSeat(calendarId);
		return new ResponseEntity<List<String>>(b, HttpStatus.OK);
	}

	@PostMapping()
	public synchronized Object post(@RequestBody Map<String, Object> body) {
		ArrayList<String> seatCodes = (ArrayList<String>)(body.get("seatCodes"));
		Booking booking = new Booking();
		booking.setUserId(body.get("userId").toString());
		booking.setCalendarId(body.get("calendarId").toString());
		if(!checkSeats(seatCodes,booking.getCalendarId())){
			return new ResponseEntity<String>("seat incorrect!", HttpStatus.BAD_REQUEST);
		}
		//payment
		try {
			pay.charge(body.get("token").toString(), Double.parseDouble(body.get("amount").toString()), 
					body.get("cusId").toString(),Integer.parseInt(body.get("save").toString()), body.get("userId").toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		booking.setId(UUID.randomUUID().toString());
		Booking b = bookingRepository.save(booking);
		Iterator<String> iter = seatCodes.iterator();
		Seat s;
		while (iter.hasNext()) {
			s = new Seat();
			s.setId(UUID.randomUUID().toString());
			s.setBookingCode(booking.getId());
			s.setSeatCode(iter.next().toString());
			seatRepository.save(s);
		}
		return new ResponseEntity<Booking>(b, HttpStatus.CREATED);

	}

	public boolean checkSeats(ArrayList<String> seatCodes, String calendarId) {
		Iterator<String> seat = seatCodes.iterator();
		ArrayList<String> bookedSeats = seatRepository.getAlreadyBookingSeat(calendarId);
		while (seat.hasNext()) {
			String c = seat.next();
			boolean a = StaticCons.seatCode.contains(c);
			boolean b = bookedSeats.contains(c);
			if (!StaticCons.seatCode.contains(c) || bookedSeats.contains(c)) {
				return false;
			}
		}
		return true;
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
