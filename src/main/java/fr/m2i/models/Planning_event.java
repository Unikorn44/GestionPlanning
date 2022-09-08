package fr.m2i.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


//@Entity
//@Table(name="planning_eventTable")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}
	
	public Planning_event() {
		
	}
	
	public Planning_event(Event event, Planning planning) {
		this.event = event;
		this.planning = planning;
	}
}
