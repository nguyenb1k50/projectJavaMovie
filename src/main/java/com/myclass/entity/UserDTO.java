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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myclass.validate.CustomUniqueConstraint;

import net.bytebuddy.implementation.bind.annotation.Empty;

@Entity
@Table(name="users",uniqueConstraints = @UniqueConstraint (columnNames = {"username","email"}))
public class UserDTO {
	public interface CreateGroup {}
	public interface UpdateGroup {}
	@Id
	private String id;
	@Column(unique = true)
	@NotEmpty(groups = CreateGroup.class)
	@CustomUniqueConstraint(message = "email duplicate!",fieldName = "email",groups = CreateGroup.class)
	private String email;
	@Column(unique = true)
	@NotEmpty(groups = CreateGroup.class)
	@CustomUniqueConstraint(message = "username duplicate!", fieldName ="username",groups = CreateGroup.class)
	private String username;
	private String password;
	private String avatar;
	private String phone;
	private String address;
	private String website;
	private String facebook;
	private Boolean active;
	private String activeToken;
	@Column(nullable = true)
	private Integer visaCard;
	private String stripeCusId;
	public int getVisaCard() {
		return visaCard;
	}

	public void setVisaCard(int visaCard) {
		this.visaCard = visaCard;
	}

	public String getStripeCusId() {
		return stripeCusId;
	}

	public void setStripeCusId(String stripeCusId) {
		this.stripeCusId = stripeCusId;
	}

	@Column(name="role_id")
	private String roleId;
	
	@ManyToOne
	@JoinColumn(name="role_id", foreignKey = @ForeignKey(name="fk_user_role"), 
		insertable = false, updatable = false)
	@JsonIgnore
	private Role role;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Booking> bookings;

	public UserDTO() {}

	public UserDTO(String id, String email, String username, String password, String avatar, String phone, String address,
			String website, String facebook, String roleId) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.phone = phone;
		this.address = address;
		this.website = website;
		this.facebook = facebook;
		this.roleId = roleId;
		this.active = false;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getActiveToken() {
		return activeToken;
	}

	public void setActiveToken(String activeToken) {
		this.activeToken = activeToken;
	}
}
