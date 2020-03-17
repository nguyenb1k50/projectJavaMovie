package com.myclass.entity;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	private String id;
	
	@NotBlank(message = "Tên không được bỏ trống!")
	@Length(min = 4, message = "Tên không được ít hơn 4 ký tự!")
	private String name;
	
	@NotBlank(message = "Mô tả không được bỏ trống!")
	private String description;
	
	@OneToMany(mappedBy="role", fetch = FetchType.LAZY)
	private List<UserDTO> users;

	public Role() {
	}

	public Role(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}