package com.myclass.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cinemars")
public class Cinemar {

	@Id
	private String id;
	
	@Column(name="cinemar_name")
	private String title;
	
	@Column(name="cinemar_local")
	private String location;
	
	@OneToMany(mappedBy="cinemar", fetch = FetchType.LAZY)
	private List<Seat> seats;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Cinemar() {
		super();
	}

	public Cinemar(String id, String title, String location) {
		super();
		this.id = id;
		this.title = title;
		this.location = location;
	}
	
	
}
