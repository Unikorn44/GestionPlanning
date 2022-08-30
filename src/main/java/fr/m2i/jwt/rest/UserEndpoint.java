package fr.m2i.jwt.rest;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fr.m2i.models.Login;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/login2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class UserEndpoint {
	
	@Inject
	private KeyGenerator keyGenerator;
	
	 @Inject
	    private Logger logger;
	 
	 @Context
	    private UriInfo uriInfo;
	 
	 private EntityManagerFactory factory;
	 
	 private EntityManager em;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("login") String login, @FormParam("password") String password) {
		try {
			
			logger.info("### login/password : " + login + "/" + password);
			
			// Authentifier user avec login et password
			authenticate(login, password);
			
			// Emet un token pour l'user
			String token = issueToken(login);
			
			// Renvoie le token sur la réponse
			return Response.ok().header(AUTHORIZATION, "Bearer" + token).build();
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
	 
	 // Création d'un token au user qui se connecte
	 private String issueToken(String login) {
		 Key key = keyGenerator.generateKey();
		 String jwtToken = Jwts.builder()
				 .setSubject(login)
				 .setIssuer(uriInfo.getAbsolutePath().toString())
				 .setIssuedAt(new Date())
				 .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
				 .signWith(SignatureAlgorithm.HS512, key)
				 .compact();
		 logger.info("#### generating token for a key : " + jwtToken + " - " + key);
		 return jwtToken;
	 }
	 
	 private Date toDate(LocalDateTime localDateTime) {
		 return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	 }

}
