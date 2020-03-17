package com.myclass.dto;

public class UserDto {
 
	private String id;
	private String email;
	private String username;
	private String roleName;
	
	public UserDto() {}
	
	public UserDto(String id, String email, String username, String roleName) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.roleName = roleName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
