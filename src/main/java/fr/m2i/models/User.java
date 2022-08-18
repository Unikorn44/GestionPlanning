package fr.m2i.models;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity(name="User")
@Table(name="userTable")
@NamedQueries({
	@NamedQuery(name="selectAllUsers",
				query="SELECT u FROM User u"),
	@NamedQuery(name="selectUserById",
				query="SELECT u FROM User u WHERE u.id = :id"),
})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Basic
	@Column(name="first_name")
	private String first_name;
	
	@Basic
	@Column(name="last_name")	
	private String last_name;
	
	@Basic
	@Column(name="city")
	private String city;
	
	@Basic
	@Column(name="birthday_date")
	private Date birthday_date;

	@Basic
	@Column(name="phone_number")
	private String phone_number;

	@Basic
	@Column(name="email")
	private String email;

	@Basic
	@Column(name="admin")
	private boolean admin;

	@Basic
	@Column(name="picture")
	private String picture;

	//Association d'un Planning à un User
	@OneToOne
	@JoinColumn(name="id_planning")
	private Planning planning;
	
	public Planning getPlanning() {
		return planning;
	}
	public void setPlanning(Planning planning) {
		this.planning = planning;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Date getBirthday_date() {
		return birthday_date;
	}
	public void setBirthday_date(Date birthday_date) {
		this.birthday_date = birthday_date;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
