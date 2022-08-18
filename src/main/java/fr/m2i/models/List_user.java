package fr.m2i.models;


import javax.persistence.Column;

import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Table(name="list_userTable")
public class List_user {
		
	//Association à un premier user
	@ManyToOne
	@Column(name="id_first_user")
	private User firstUser;
	
	//Association à un second user
	@ManyToOne
	@Column(name="id_second_user")
	private User secondUser;

}
