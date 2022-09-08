package fr.m2i.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="planningtable")
@NamedQueries({
	@NamedQuery(name="selectAllPlannings", query="select element from Planning element"),
	@NamedQuery(name="selectPlanningById", query="select element from Planning element where element.id = :id"),
	@NamedQuery(name="selectPlanningByUserId", query="SELECT element "
			+ "FROM Planning element "
			+ "INNER JOIN element.user u "
			+ "WHERE u.id = :id"),
	@NamedQuery(name="deletePlanningById", query="delete from Planning element where element.id = :id")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "deleteNatifTest",query = "DELETE FROM planningtable WHERE id=:id")
})
public class Planning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Basic
	@Column(name="export")
	private boolean export;
	
	@Basic
	@Column(name="acces")
	private boolean acces;
	
	@Basic
	@Column(name="modification")
	private boolean modification;
	
	//Association à table multi
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="planning_eventTable", 
		joinColumns=@JoinColumn(name="id_planning"), 
		inverseJoinColumns=@JoinColumn(name="id_event"))
	@JsonIgnore
	private List<Event> events = new ArrayList<>();
	
	@OneToOne(mappedBy = "planning")
	@JsonBackReference
    private User user;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
	}
	
	public boolean isAcces() {
		return acces;
	}
	public void setAcces(boolean acces) {
		this.acces = acces;
	}
	
	public boolean isModification() {
		return modification;
	}
	public void setModification(boolean modification) {
		this.modification = modification;
	}

	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
