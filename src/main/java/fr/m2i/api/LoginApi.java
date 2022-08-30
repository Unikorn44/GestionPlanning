package fr.m2i.api;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.time.Instant;
import java.util.Date;

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

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import fr.m2i.models.Login;

@Path("/login")
public class LoginApi {
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
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
			String token = issueToken(login);
						
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
	 
	 // Création d'un token au user qui se connecte
	 private String issueToken(String login) {		 
		 try {
			 
			 ECKey key = new ECKeyGenerator(Curve.P_256)
			    .keyID("1")
			    .generate();
			 
			 JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
					    .type(JOSEObjectType.JWT)
					    .keyID(key.getKeyID())
					    .build();
			 
			 JWTClaimsSet payload = new JWTClaimsSet.Builder()
					    //.issuer(uriInfo.getAbsolutePath().toString())
					    .issueTime(new Date())
					    .subject(login)
					    .expirationTime(Date.from(Instant.now().plusSeconds(600)))
					    .build();
			 
			 
			 SignedJWT signedJWT = new SignedJWT(header, payload);
			 signedJWT.sign(new ECDSASigner(key.toECPrivateKey()));
			 String jwtToken = signedJWT.serialize();
			 
			 System.out.print(jwtToken);
			 return jwtToken;
		 
		 } catch (Exception e) {
			 System.out.println(e);
		 }
		 
		 return null;
	 }

}
