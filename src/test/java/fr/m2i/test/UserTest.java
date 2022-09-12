package fr.m2i.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.m2i.models.Login;
import fr.m2i.models.Planning;
import fr.m2i.models.User;
import fr.m2i.servlets.PageAdmin;

@ExtendWith(MockitoExtension.class)
public class UserTest {

	PageAdmin paj;
	
	@BeforeEach
	public void init() {
		this.paj = new PageAdmin();		
	}

	@AfterEach
	public void cleanUpEach() {
		this.paj = null;
	}	
	
	@Test //test User creation 
	public void UserCreationTest() {
		
		Date dateUser = new Date(25-12-1978);		
		final User userCreationTested = paj.createUser("John", "Doe", "Huston", dateUser, "06-07-08-09-08", "john@Doe.com");
		
		assertThat(userCreationTested).isExactlyInstanceOf(User.class);
		assertThat(userCreationTested.getAdmin()).isFalse();		
	}
	
	@Test //Test Login Creation
	public void LoginCreationTest() {
		
		Date dateUser = new Date(25-12-1978);
		User userCreationTested = paj.createUser("John", "Doe", "Huston", dateUser, "06-07-08-09-08", "john@Doe.com");
		
		final Login loginCreationTested = paj.createLogin(userCreationTested);
		
		assertThat(loginCreationTested).isExactlyInstanceOf(Login.class);		
	}
	
	@Test //Test Planning Creation
	public void PlanningCreationTest() {
		
		final Planning planningCreationTested = paj.createPlanning();
		
		assertThat(planningCreationTested).isExactlyInstanceOf(Planning.class);
	}
	
	@Test //Test Password creation
	public void PasswordCreationTest() {
		
		Date dateUser = new Date(25-12-1978);
		User userCreationTested = paj.createUser("John", "Doe", "Huston", dateUser, "06-07-08-09-08", "john@Doe.com");

		final String passwordCreatedTestest = paj.createPassword(userCreationTested);
		
		String pattern = "^user[0-9]{2}\\w{2}";
		boolean testPatternPassword = Pattern.matches(pattern, passwordCreatedTestest);
		
		assertThat(testPatternPassword).isTrue();		
	}
	
	
	
	
}
