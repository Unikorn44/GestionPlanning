package fr.m2i.service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenService {
	
	private ECKey key;
	
	public TokenService() {
		this.key = KeySingleton.getKey();
	}
	
	// Création d'un token au user qui se connecte
	public String issueToken(String login) {		 
		try { 		 
			JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
				    .type(JOSEObjectType.JWT)
				    .keyID(this.key.getKeyID())
				    .build();
		 
			JWTClaimsSet payload = new JWTClaimsSet.Builder()
				    .issueTime(new Date())
				    .subject(login)
				    .expirationTime(Date.from(Instant.now().plusSeconds(600)))
				    .build();
		 
			SignedJWT signedJWT = new SignedJWT(header, payload);
			signedJWT.sign(new ECDSASigner(this.key.toECPrivateKey()));
			String jwtToken = signedJWT.serialize();
			return jwtToken;
	 
		} catch (Exception e) {
			System.out.println(e);
		}
	 
		return null;
 }

		// Vérification token
		public Boolean isValid(String token) {
			try {
				System.out.println("je suis dans la méthod valid");
				System.out.println(token);
				Boolean valid = SignedJWT.parse(token).verify(new ECDSAVerifier(this.key.toECPublicKey()));
				return valid;
			} catch (JOSEException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return false;
		}

}
