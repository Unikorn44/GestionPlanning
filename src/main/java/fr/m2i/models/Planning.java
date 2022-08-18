package fr.m2i.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="planningTable")
@NamedQueries({
	@NamedQuery(name="selectAllPlannings", query="select element from Planning element"),
	@NamedQuery(name="selectPlanningById", query="select element from Planning element where element.id = :id"),
	@NamedQuery(name="deletePlanningById", query="delete from Planning element where element.id = :id")
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
	@OneToMany
	private List<Planning_event> planningEvents = new ArrayList<>();
	
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

	
	

	
}
