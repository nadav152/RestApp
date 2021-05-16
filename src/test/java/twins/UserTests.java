package twins;

import static org.assertj.core.api.Assertions.assertThat;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import twins.additionalClasses.NewUserDetails;
import twins.additionalClasses.UserId;
import twins.boundaries.UserBoundary;
import twins.data.UserRole;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {

	
	/*
	 * 
	 *  This class is for User URL HTML test 
	 * 
	 */
	
	private int port;
	private String url; // http://localhost:port/twins/users
	private RestTemplate restTemplate;
	
	@LocalServerPort // inject port number to test case
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/twins/users";
		System.err.println(this.url);
		this.restTemplate = new RestTemplate();
	}
	
	@AfterEach
	public void tearDown() {
		/// clean database
		//TODO fix to the right url
		//this.restTemplate
		//	.delete(this.url); 
	}
	
	@Test
	public void testCreateUser() throws Exception{
		NewUserDetails userDetails = new NewUserDetails();
		userDetails.setEmail("gunman@gmail.com");
		userDetails.setRole(UserRole.MANAGER);
		userDetails.setUsername("cowboy77");
		userDetails.setAvatar("cb");
		
		UserBoundary response = this.restTemplate
				.postForObject(this.url, userDetails, UserBoundary.class);
		
		assertThat(response.getAvatar())
			.isEqualTo("cb");
		System.err.println("***"+response);
	}
	
	@Test 
	public void testUpdateUserAndValidateTheDatabaseIsUpdated() {
		// GIVEN the database contains a user
		NewUserDetails userDetails = new NewUserDetails();
		userDetails.setEmail("cowboy4life@gmail.com");
		userDetails.setRole(UserRole.PLAYER);
		userDetails.setUsername("cowboy11");
		userDetails.setAvatar("cbn");
		
		UserBoundary response = this.restTemplate
			.postForObject(this.url, userDetails, UserBoundary.class);
		
		// WHEN I invoke PUT /messages/{messageId} and {"message":"new value"}
		UserBoundary update = new UserBoundary();
		update.setUserID(new UserId("cowboyEra","cowboy4life@gmail.com"));
		update.setRole("PLAYER");
		update.setUserName("cowboy11");
		update.setAvatar("cbn");
		this.restTemplate
			.put(this.url + "/{userSpace}/{userEmail}", update, response.getUserID().getSpace(), response.getUserID().getEmail());
		
		// THEN the database message is updated
		assertThat(this.restTemplate
			.getForObject(this.url + "/{userSpace}/{userEmail}", UserBoundary.class, response.getUserID().getSpace(), response.getUserID().getEmail())
			.getUserID())
		
			.isEqualTo(update.getUserID())
			.isNotEqualTo(response.getUserID());
	}
}
