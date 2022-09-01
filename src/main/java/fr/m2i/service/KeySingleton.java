package fr.m2i.service;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

public final class KeySingleton {
	
	private static ECKey key;
	
	private KeySingleton() {
	}
	
	// Création key
    public static ECKey getKey() {
        if (key == null) {
            try {
            	key = new ECKeyGenerator(Curve.P_256)
				.keyID("1")
				.generate();
			} catch (JOSEException e) {
				e.printStackTrace();
			}
        }
        return key;
    }
}
