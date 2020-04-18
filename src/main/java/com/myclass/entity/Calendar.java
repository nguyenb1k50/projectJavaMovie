package com.myclass.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "calendars")
public class Calendar {
	@Id
	private String id;
	private Date openDate;
	private ArrayList<Time> time;
	@Column(name = "movie_id")
	private String movieId;
	@ManyToOne
	@JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_movie_calendar"), insertable = false, updatable = false)
	@JsonIgnore
	private Movie movie;
	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getCinemarId() {
		return cinemarId;
	}

	public void setCinemarId(String cinemarId) {
		this.cinemarId = cinemarId;
	}

	@Column(name = "cinemar_id")
	private String cinemarId;
	@ManyToOne
	@JoinColumn(name = "cinemar_id", foreignKey = @ForeignKey(name="fk_cinemar_calendar"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Cinemar cinemar;
	
	@OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY)
	private List<Booking> bookings;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Calendar() {
		super();
	}

	public ArrayList<Time> getTime() {
		return time;
	}

	public void setTime(ArrayList<Time> time) {
		this.time = time;
	}

	public Cinemar getCinemar() {
		return cinemar;
	}

	public void setCinemar(Cinemar cinemar) {
		this.cinemar = cinemar;
	}

	public Calendar(String id, Date openDate, ArrayList<Time> time, Movie movie) {
		super();
		this.id = id;
		this.openDate = openDate;
		this.time = time;
		this.movie = movie;
	}

}
