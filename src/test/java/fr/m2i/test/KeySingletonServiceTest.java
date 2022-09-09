package fr.m2i.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;

import fr.m2i.service.KeySingletonService;

import org.mockito.Mock;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class KeySingletonServiceTest {

	
	
	
	@Test
	public void ServiceKeyTest() throws JOSEException {
		
		//ECKeyGenerator kgb = Mockito.mock(ECKeyGenerator.class, Mockito.withSettings().useConstructor(Curve.P_256));
		
		//ECKey anyKey = null;
		//when(kgb.keyID("1").generate()).thenReturn(any(ECKey.class));
		
		KeySingletonService kiss = new KeySingletonService();
		
		final ECKey keyGenerated = kiss.getKey();
		//verify(kgb).keyID("1").generate();
		
		assertThat(keyGenerated).isExactlyInstanceOf(ECKey.class);
		
	}
	
}
