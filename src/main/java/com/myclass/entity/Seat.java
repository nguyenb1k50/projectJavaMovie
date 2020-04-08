package com.myclass.entity;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	@NotNull
	private String id;
	
	@Column(name="seat_name")
	private String name;
	
	@Column(name="seat_status")
	private String status;
	
	@Column(name = "cinemar_id")
	private String cinemarId;
	
	@ManyToOne
	@JoinColumn(name = "cinemar_id", foreignKey = @ForeignKey(name="fk_seat_cinemar"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Cinemar cinemar;

	@OneToMany(mappedBy="seat", fetch = FetchType.LAZY)
	private List<Booking> bookings;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Seat() {
		super();
	}

	public Seat(@NotNull String id, String name, String status, Seat seat) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	
	
	

}
