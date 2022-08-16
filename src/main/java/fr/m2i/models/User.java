package fr.m2i.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="userTable")
@NamedQueries({
	@NamedQuery(name="selectAllUsers", query="select element from User element"),
	@NamedQuery(name="selectUserById", query="select element from User element where element.id = :id"),
	@NamedQuery(name="deleteUserById", query="delete from User element where element.id = :id")
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
	@Column(name="login")
	private String login;
	
	@Basic
	@Column(name="password")	
	private String password;
	
	@Basic
	@Column(name="admin")	
	private boolean admin;
	
	@Basic
	@Column(name="picture")	
	private String picture;
	
	@Basic
	@Column(name="id_planning")	
	private int id_planning;
	
	//Association d'un Planning à un User
	@OneToOne
	@JoinColumn(name="id_planning")
	private Planning planning;
	
	//Association à un liste users
	@OneToMany
	private List<User> users = new ArrayList<>();
	
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
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public int getId_planning() {
		return id_planning;
	}
	public void setId_planning(int id_planning) {
		this.id_planning = id_planning;
	}

	
	
}
