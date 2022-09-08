package fr.m2i.api;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.m2i.models.Event;
import fr.m2i.models.Planning;
import fr.m2i.models.User;

@Path("/user")
public class UserApi {
	EntityManagerFactory factory;
	EntityManager em;
	
	// Récupération de tous les users
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAllUser() {
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		System.out.println("je suis dans la requête");
		List<User> userListRecup = em.createNamedQuery("selectAllUsers", User.class).getResultList();

		em.close();
		factory.close();
		
		return userListRecup;
	}
	
	// Récupération des informations User pour un user
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUserById(@PathParam("id") int id){
		System.out.println("je suis dans la requête");
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		System.out.println("je suis dans la requête");
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
		
		em.close();
		factory.close();
		
		return users;
	}
	
	// UPDATE informations user
	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(@PathParam("id") int id, User user)
	{

		System.out.println("je suis dans le update");
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		Query q = em.createNamedQuery("selectUserById", User.class);
		q.setParameter("id", id);
		User userDb = (User) q.getSingleResult();
		
		userDb.setFirst_name(user.getFirst_name());
		userDb.setLast_name(user.getLast_name());
		userDb.setCity(user.getCity());
		userDb.setBirthday_date(user.getBirthday_date());
		userDb.setPhone_number(user.getPhone_number());
		userDb.setEmail(user.getEmail());
		userDb.setPicture(user.getPicture());
				
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	
	// Récupération de tous les events d'un utilisateur
	@GET
	@Path("/{id}/events")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> findAllEventsByUser(@PathParam("id") int id) {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("selectPlanningByUserId", Planning.class);
		q.setParameter("id", id);
		
		Planning planningUser = (Planning) q.getSingleResult();
		List<Event> events = planningUser.getEvents();
			
		factory.close();
			
		return events;
	}
}
