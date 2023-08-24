package com.smart.entities;

import java.util.ArrayList;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	 @NotBlank(message = "email cannot be empty")
	private String email;
	 @NotBlank(message = "name is required")
	 @Size(min = 3,max = 20,message = "min 3 max 20 char are allowed")
	private String name;
	private String password;
	private String role;
	@Column(length = 500)
	private String about;
	private boolean enabled;
	private String imageurl;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 private  List<Contact> contacts=new ArrayList<Contact>();
			 
	
	
	
	public List<Contact> getContacts() {
		return contacts;
	}




	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}




	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public User(int id, String email, String name, String password, String role, String about, boolean enabled,
			String imageurl) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
		this.about = about;
		this.enabled = enabled;
		this.imageurl = imageurl;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", role=" + role
				+ ", about=" + about + ", enabled=" + enabled + ", imageurl=" + imageurl + "]";
	}
	
	
	

}
