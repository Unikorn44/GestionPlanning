package fr.m2i.api;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.m2i.models.Login;
import fr.m2i.service.TokenService;

@Path("/login")
public class LoginApi {
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	private TokenService tokenService;
	
	//private UriInfo uriInfo;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("login") String login, @FormParam("password") String password) {
		
		try {
			
			factory = Persistence.createEntityManagerFactory("UnityPersist");
			em = factory.createEntityManager();
			
			// Authentifier user avec login et password
			authenticate(login, password);
			
			// Emet un token pour l'user
			this.tokenService = new TokenService();
			String token = tokenService.issueToken(login);
						
			// Renvoie le token sur la réponse
			em.close();
			factory.close();
			
			// Renvoie le token sur la réponse
			return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
		} catch (Exception e) {
			return Response.status(UNAUTHORIZED).build();
		}
		
	}
	
	// Vérification en base de données si le user est connu
	 private void authenticate(String login, String password) throws Exception {
		 factory = Persistence.createEntityManagerFactory("UnityPersist");
		 em = factory.createEntityManager();
		 
		 TypedQuery<Login> query = em.createNamedQuery("findByloginpassword", Login.class);
	     	query.setParameter("login", login);
	     	query.setParameter("password", password);
	     Login logFound = query.getSingleResult();

	     if (logFound == null) {	    	 
	    	 throw new SecurityException("Invalid user/password");
	     }
	 }
}
