package fr.m2i.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;


@Entity
@Table(name="planning_eventTable")
public class Planning_event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	//Association à un event
	@ManyToOne
	@JoinColumn(name="id_event")
	private Event event;
	
	//Association à un planning
	@ManyToOne
	@JoinColumn(name="id_planning")
	private Planning planning;
	
	
	

}
