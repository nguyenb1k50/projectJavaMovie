package com.myclass.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cinemas")
public class Cinemar {

	@Id
	private String id;
	
	@Column(name="cinemar_name")
	private String title;
	
	@Column(name="lat")
	private double lat;
	
	@Column(name="lng")
	private double lng;
	
//	@OneToMany(mappedBy="cinemar", fetch = FetchType.LAZY)
//	private List<Seat> seats;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Cinemar() {
		super();
	}

	public double getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

//	public List<Seat> getSeats() {
//		return seats;
//	}
//
//	public void setSeats(List<Seat> seats) {
//		this.seats = seats;
//	}

	
	
	
}
