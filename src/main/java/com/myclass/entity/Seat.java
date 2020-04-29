package com.myclass.entity;

import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seats")
public class Seat {
	
	@Id
	@Column(name="id")
	@NotNull
	private String id;
	
	@Column(name="seat_code")
	private String seatCode;
	
	@Column(name="booking_id")
	private String bookingId;
	
	@ManyToOne
	@JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name="fk_seat_booking"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Booking booking;
	
	private Time showTime;
	
	public Time getShowTime() {
		return showTime;
	}

	public void setShowTime(Time showTime) {
		this.showTime = showTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Seat() {
		super();
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingId = bookingCode;
	}

}
