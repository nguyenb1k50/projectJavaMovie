package com.myclass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bookings")
public class Booking {
	@Id
	private String id;

	@Column(name = "user_id")
	private String userId;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_booking_user"), insertable = false, updatable = false)
	@JsonIgnore
	private UserDTO user;

	@Column(name = "calendar_id")
	private String calendarId;

	@ManyToOne
	@JoinColumn(name = "calendar_id", foreignKey = @ForeignKey(name = "fk_calendar_booking"), insertable = false, updatable = false)
	@JsonIgnore
	private Calendar calendar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Booking() {
		super();
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public Booking(String userId, UserDTO user, String calendarId, Calendar calendar) {
		super();
		this.userId = userId;
		this.user = user;
		this.calendarId = calendarId;
		this.calendar = calendar;
	}

}
