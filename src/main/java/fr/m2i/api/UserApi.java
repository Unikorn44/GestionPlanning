package fr.m2i.api;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.m2i.models.User;

@Path("/user")
public class UserApi {
	EntityManagerFactory factory;
	EntityManager em;
	
	// Récupération des informations User pour un user
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUserById(@PathParam("id") int id){
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("selectUserById", User.class);
		q.setParameter("id", id);
		User user = (User) q.getSingleResult();
		
		em.close();
		factory.close();
		
		return user;
		
	}
	
	// Récupération des informations User pour les contacts d'un user
	@GET
	@Path("/{id}/contact")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAllContactByUser(@PathParam("id") int id) {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("selectContactForUser", User.class);
		q.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<User> users = q.getResultList();
		
		factory.close();
		
		return users;
	}
	
	// UPDATE informations user
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void updateUser(@FormParam("id") int id, @FormParam("first_name") String first_name,
			@FormParam("last_name") String last_name, @FormParam("city") String city,
			@FormParam("birthday_date") Date birthday_date, @FormParam("phone_number") String phone_number,
			@FormParam("email") String email, @FormParam("admin") Boolean admin,
			@FormParam("picture") String picture) {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
	}
	
}
