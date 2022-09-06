package fr.m2i.api;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.sql.Date;
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
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void updateUser(@PathParam("id") int id, @FormParam("first_name") String first_name,
			@FormParam("last_name") String last_name, @FormParam("city") String city,
			@FormParam("birthday_date") Date birthday_date, @FormParam("phone_number") String phone_number,
			@FormParam("email") String email, @FormParam("picture") String picture) 
	{
				
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		Query q = em.createNamedQuery("selectUserById", User.class);
		q.setParameter("id", id);
		User user = (User) q.getSingleResult();
		
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setCity(city);
		user.setBirthday_date(birthday_date);
		user.setPhone_number(phone_number);
		user.setEmail(email);
		user.setPicture(picture);
				
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
}
