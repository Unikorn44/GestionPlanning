package fr.m2i.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import fr.m2i.models.Login;

@Path("/login")
public class LoginApi {
	
	EntityManagerFactory factory;
	EntityManager em;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Login login(@FormParam("login") String login, @FormParam("password") String password) {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("findByloginpassword", Login.class);
		q.setParameter("login", login);
		q.setParameter("password", password);
		
		Login loginFound = (Login) q.getSingleResult();
		
		System.out.print(loginFound);
		
		em.close();
		factory.close();

		return loginFound;
	}

}
