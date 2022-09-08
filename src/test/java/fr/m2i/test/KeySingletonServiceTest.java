package fr.m2i.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nimbusds.jose.jwk.ECKey;

import fr.m2i.service.KeySingletonService;

import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class KeySingletonServiceTest {

	@Mock
	KeySingletonService kss;
	
	@BeforeEach
	public void init() {
		kss = new KeySingletonService();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void ServiceKeyTest() {
		kss.getKey();
		when(kss.getKey()).thenReturn(null);
		kss.getKey();
		final ECKey keyGenerated = kss.getKey();
		verify(kss).getKey();
		
	}
	
}
