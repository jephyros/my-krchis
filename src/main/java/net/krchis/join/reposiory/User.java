package net.krchis.join.reposiory;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {
	
	

	@Column(nullable=false, length=20, unique=true)
	@JsonProperty
	private String userId;	
	
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;
	
	
	public void updateUser(User user) {
		this.password = user.password;
		this.name = user.name;
		this.email = user.email;
		
		
	}
	@Override
	public String toString() {
		return "User [" + super.toString() + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}
	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public boolean matchPassword(String newPassword) {
		if (newPassword == null) {
			return false;
		}
		
		return newPassword.equals(password);
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
