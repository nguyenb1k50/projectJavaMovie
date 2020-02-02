package com.myclass.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "catagories")
public class Catagory {
	@Id
	private String id;
	
	private String title;
	
	@OneToMany(mappedBy="catagory", fetch = FetchType.LAZY)
	private List<Movie> movies;

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

	public Catagory(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Catagory() {
		super();
	}
	

}
