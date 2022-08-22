package fr.m2i.api;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.m2i.models.User;

@Path("/user")
public class UserApi {
	EntityManagerFactory factory;
	EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAll() {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		List<User> users = em.createNamedQuery("selectAllUsers", User.class).getResultList();
		
		em.close();
		factory.close();
		
		return users;
	}
	
	// Récupération des informations User pour un user
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findUserById(@PathParam("id") int id){
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("selectUserById", User.class);
		q.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<User> user = q.getResultList();
		
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
		
}
