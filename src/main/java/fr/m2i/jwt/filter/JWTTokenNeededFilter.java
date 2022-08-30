package fr.m2i.jwt.filter;

import java.security.Key;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
	
	@Inject
	private Logger logger;
	
	@Inject
	private KeyGenerator keyGenerator;
	
	@SuppressWarnings("deprecation")
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		 // Récupère l'en-tête d'autorisation HTTP à partir de la requête
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        logger.info("#### authorizationHeader : " + authorizationHeader);
        
        // Vérifie si l'en-tête HTTP Authorization est présent et formaté correctement
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        	logger.severe("### invalid authorizationHeader : " + authorizationHeader);
        	throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extrait le token de l'en-tête d'autorisation HTTP
        String token = authorizationHeader.substring("Bearer".length()).trim();
        
        try {
        	// Validation du token
        	Key key = keyGenerator.generateKey();
        	Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        	logger.info("### valid token:" + token);
        } catch (Exception e) {
        	logger.severe("## invalid token:" + token);
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
       
	}

}
