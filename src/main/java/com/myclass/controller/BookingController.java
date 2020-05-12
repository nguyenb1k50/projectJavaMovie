package com.myclass.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.ModelAndView;

import com.myclass.config.StaticCons;
import com.myclass.dto.excelDTO;
import com.myclass.entity.Booking;
import com.myclass.entity.Catagory;
import com.myclass.entity.Seat;
import com.myclass.entity.UserDTO;
import com.myclass.repository.BookingRepository;
import com.myclass.repository.CatagoryRepository;
import com.myclass.repository.SeatRepository;
import com.myclass.repository.UserRepository;
import com.myclass.service.ExcelReportView;
import com.myclass.service.MailService;

@RestController
@RequestMapping("api/booking")
public class BookingController {

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	SeatRepository seatRepository;
	@Autowired
	PaymentController pay;
	@Autowired
	private MailService mail;
	@Autowired
	UserRepository userRepository;

	@GetMapping("")
	public Object get() {
		List<Booking> b = bookingRepository.getAllBooking();
		return new ResponseEntity<List<Booking>>(b, HttpStatus.OK);
	}

	@GetMapping("/seats/{calendarId}")
	public Object get(@PathVariable String calendarId, @RequestParam Time showTime) {
		List<String> b = seatRepository.getAlreadyBookingSeat(calendarId, showTime);
		return new ResponseEntity<List<String>>(b, HttpStatus.OK);
	}

	@PostMapping()
	public synchronized Object post(@RequestBody Map<String, Object> body) {
		String currentUserName;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		} else {
			throw new AccessDeniedException("đăng nhâp đi bạn!");
		}
		UserDTO u = userRepository.findByUsername(currentUserName);
		ArrayList<String> seatCodes = (ArrayList<String>) (body.get("seatCodes"));
		Time showTime = Time.valueOf(body.get("showTime").toString());
		Booking booking = new Booking();
		booking.setUserId(u.getId());
		booking.setCalendarId(body.get("calendarId").toString());
		if (!checkSeats(seatCodes, booking.getCalendarId(), showTime)) {
			return new ResponseEntity<String>("seat incorrect!", HttpStatus.BAD_REQUEST);
		}
		// payment
		try {
			pay.charge(body.get("token").toString(), Double.parseDouble(body.get("amount").toString()),
					body.get("cusId").toString(), Integer.parseInt(body.get("save").toString()), u.getId());
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
			s.setShowTime(showTime);
			seatRepository.save(s);
		}
		sendBookingDetailMail(b.getId(), u.getEmail());
		return new ResponseEntity<Booking>(b, HttpStatus.CREATED);

	}

	@GetMapping("/report")
	public ModelAndView getExcel() {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		ExcelReportView ex = new ExcelReportView("System cinema report " + month + "-" + year, "systemcinemareport");
		//report 1		
		List<String[]> data = bookingRepository.getListHotestMovie();
        ex.addStaticAttribute("report1", data);
        //report 2
        List<String[]> data2 = bookingRepository.getMostViewTimeFrame();
        ex.addStaticAttribute("report2", data2);
		return new ModelAndView(ex);
	}
	
	@GetMapping("/report/hotmovie")
	public Object report1() {
		
		return new ResponseEntity<List<String[]>>(bookingRepository.getListHotestMovie(), HttpStatus.OK);
	}
	
	@GetMapping("/report/mostviewtime")
	public Object report2() {
		
		return new ResponseEntity<List<String[]>>(bookingRepository.getMostViewTimeFrame(), HttpStatus.OK);
	}

	public boolean checkSeats(ArrayList<String> seatCodes, String calendarId, Time showTime) {
		Iterator<String> seat = seatCodes.iterator();
		ArrayList<String> bookedSeats = seatRepository.getAlreadyBookingSeat(calendarId, showTime);
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

	public void sendBookingDetailMail(String bookingId, String userMail) {
		List<String[]> bookingDetail = bookingRepository.getBookingDetail(bookingId);
		mail.sendBookingDetailMailTemplate(userMail, "Booking Detail", bookingDetail);
	}

}
