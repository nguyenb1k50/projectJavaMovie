package com.myclass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ratting")
public class Ratting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "movie_id")
	private String movieId;
	@Column(name = "user_id")
	private String userId;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name="fk_movie_ratting"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="fk_user_ratting"),
	insertable = false, updatable = false)
	@JsonIgnore
	private UserDTO user;
	
	private int star;
	public Ratting(String movieId, String userId, int star) {
		this.movieId = movieId;
		this.userId = userId;
		this.star = star;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public Ratting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	
}
