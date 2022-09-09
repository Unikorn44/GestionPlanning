package fr.m2i.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nimbusds.jose.JOSEException;

import com.nimbusds.jose.jwk.ECKey;

import fr.m2i.service.KeySingletonService;


@ExtendWith(MockitoExtension.class)
public class KeySingletonServiceTest {

	
	
	
	@Test
	public void ServiceKeyTest() throws JOSEException {
		
		KeySingletonService kiss = new KeySingletonService();
		
		final ECKey keyGenerated = kiss.getKey();
		
		assertThat(keyGenerated).isExactlyInstanceOf(ECKey.class);
		
	}
	
}
