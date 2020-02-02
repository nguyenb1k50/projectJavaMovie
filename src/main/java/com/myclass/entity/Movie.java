package com.myclass.entity;

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
@Table(name = "movies")
public class Movie {
	@Id
	private String id;
	private String title;
	private int duration;
	private String language;
	private String description;
	private String traller_url;
	private String country;
	
	@Column(name = "catagory_id")
	private String catagoryId;
	
	@ManyToOne
	@JoinColumn(name = "catagory_id", foreignKey = @ForeignKey(name="fk_movie_catagory"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Catagory catagory;
	
	@OneToMany(mappedBy="movie", fetch = FetchType.LAZY)
	private List<Calendar> calendars;
	
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTraller_url() {
		return traller_url;
	}

	public void setTraller_url(String traller_url) {
		this.traller_url = traller_url;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(String catagoryId) {
		this.catagoryId = catagoryId;
	}

	public Catagory getCatagory() {
		return catagory;
	}

	public void setCatagory(Catagory catagory) {
		this.catagory = catagory;
	}

	public Movie(String id, String title, int duration, String language, String description, String traller_url,
			String country, String catagoryId, Catagory catagory) {
		super();
		this.id = id;
		this.title = title;
		this.duration = duration;
		this.language = language;
		this.description = description;
		this.traller_url = traller_url;
		this.country = country;
		this.catagoryId = catagoryId;
		this.catagory = catagory;
	}

	public Movie() {
		super();
	}


	

}
