package com.myclass.dto;

public class UserDto {
 
	private String id;
	private String email;
	private String fullname;
	private String roleName;
	
	public UserDto() {}
	
	public UserDto(String id, String email, String fullname, String roleName) {
		super();
		this.id = id;
		this.email = email;
		this.fullname = fullname;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
