package com.myclass.entity;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	private String id;
	private String imdbId;
	private int imdbRating;
	
	//"poster": "https://m.media-amazon.com/images/M/MV5BMjIwMDIwNjAyOF5BMl5BanBnXkFtZTgwNDE1MDc2NTM@._V1_SX300.jpg",
	private String poster;
	private String director;
    private String[] actors;
	private String title;
	private int duration;
	private String language;
	private String description;
	private String traller_url;
	private String country;
	private Date openDate;
	private String image;
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	private Date closeDate;
	@Transient
	private float star;
	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	@Column(name = "catagory_id")
	private String catagoryId;
	
	@ManyToOne
	@JoinColumn(name = "catagory_id", foreignKey = @ForeignKey(name="fk_movie_catagory"),
	insertable = false, updatable = false)
	@JsonIgnore
	private Catagory catagory;
	
	@OneToMany(mappedBy="movie", fetch = FetchType.LAZY)
	private List<Calendar> calendars;

//	@OneToMany(mappedBy="movie", fetch = FetchType.LAZY)
//	private List<Ratting> ratting;
	
    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
	
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



	public Movie(String id, String imdbId, int imdbRating, String poster, String director, String[] actors,
			String title, int duration, String language, String description, String traller_url, String country,
			String catagoryId, Catagory catagory, List<Calendar> calendars) {
		super();
		this.id = id;
		this.setImdbId(imdbId);
		this.setImdbRating(imdbRating);
		this.setPoster(poster);
		this.setDirector(director);
		this.actors = actors;
		this.title = title;
		this.duration = duration;
		this.language = language;
		this.description = description;
		this.traller_url = traller_url;
		this.country = country;
		this.catagoryId = catagoryId;
		this.catagory = catagory;
		this.calendars = calendars;
	}

	public Movie() {
		super();
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(int imdbRating) {
		this.imdbRating = imdbRating;
	}


	

}
