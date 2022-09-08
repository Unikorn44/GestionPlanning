package fr.m2i.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ContactTable")
@NamedQueries({
	@NamedQuery(name="selectAllContactByListId",
				query="SELECT c FROM Contact as c INNER JOIN c.listUser as l WHERE l.id = :id"),
})
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="id_list_user")
	private List_user listUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List_user getListUser() {
		return listUser;
	}

	public void setListUser(List_user listUser) {
		this.listUser = listUser;
	}
	
	public Contact(User user, List_user listUser) {
		this.user = user;
		this.listUser = listUser;
	}
	
	public Contact() {
	}

}
