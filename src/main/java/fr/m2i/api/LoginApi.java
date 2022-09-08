package fr.m2i.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import fr.m2i.models.Login;
import fr.m2i.models.User;
import fr.m2i.service.TokenService;

@Path("/login")
public class LoginApi {
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	private TokenService tokenService;
		
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<User> login(@FormParam("login") String login, @FormParam("password") String password) {
			
		// Authentifier user avec login et password
		Login log;
		log = authenticate(login, password);
		User user = log.getUser();
		// Emet un token pour l'user
		this.tokenService = new TokenService();
		String token = tokenService.issueToken(login);
			
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("authorization","Baerer " + token);

		if(user.getCompte_actif()) {			
	        return ResponseEntity.ok()
	                  .headers(responseHeaders)
	                  .body(user);
		} else {
			System.out.println("je suis dans le else");
			return ResponseEntity.badRequest()
	                  .body(user);
		}
	}
	
	// Vérification en base de données si le user est connu
	 private Login authenticate(String login, String password) {
		 factory = Persistence.createEntityManagerFactory("UnityPersist");
		 em = factory.createEntityManager();
		 
		 TypedQuery<Login> query = em.createNamedQuery("findByloginpassword", Login.class);
	     	query.setParameter("login", login);
	     	query.setParameter("password", password);
	     Login logFound = query.getSingleResult();

	     factory.close();
	     em.close();
	     
	     return logFound;
	 }
}
