package fr.m2i.models;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="Event")
@Table(name="eventTable")
@NamedQueries({
	@NamedQuery(name="selectAllEvents", query="SELECT e FROM Event e"),
	@NamedQuery(name="selectEventsByUserId", query="SELECT e "
			+ "FROM Event e "
			+ "INNER JOIN e.planningEvents pe "
			+ "INNER JOIN pe.planning p "
			+ "INNER JOIN p.user u "
			+ "WHERE u.id = :id"),
	@NamedQuery(name="deleteEventById", query="delete from Event element where element.id = :id")
})
public class Event {
	
	/*
	 *query="SELECT e "
			+ "FROM User u "
			+ "INNER JOIN u.planning p "
			+ "INNER JOIN p.planningEvents pe "
			+ "INNER JOIN pe.event e "
			+ "where u.id = :id"), 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic
	@Column(name="title")
	private String title;
	
	@Basic
	@Column(name="date_event")
	private Date date_event;
	
	@Basic
	@Column(name="start_time")
	private Time start_time;
	
	@Basic
	@Column(name="end_time")
	private Time end_time;	
	
	@Basic
	@Column(name="description")
	private String description;
	
	//Association à table multi
	@OneToMany(mappedBy="event")
	@JsonBackReference
	private List<Planning_event> planningEvents;
	
	public List<Planning_event> getPlanningEvents() {
		return planningEvents;
	}

	public void setPlanningEvents(List<Planning_event> planningEvents) {
		this.planningEvents = planningEvents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate_event() {
		return date_event;
	}

	public void setDate_event(Date date_event) {
		this.date_event = date_event;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
