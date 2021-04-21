package twins;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import twins.boundaries.ItemBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	
	/*
	 * 
	 * This class test Item URL HTTP request
	 * 
	 */
	
	
	private int port;
	private String url; // http://localhost:port/messages
	private RestTemplate restTemplate;
	
	@LocalServerPort // inject port number to test case
	public void setPort(int port) {
		this.port = port;
	}
	
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/twins/items";
		System.err.println(this.url);
		this.restTemplate = new RestTemplate();
	}
	
	@AfterEach
	public void tearDown() {
		/// clean database
		this.restTemplate
			.delete(this.url); 
//			.delete(this.url + "/{space}/{email}", "myspace", "admin@afeka.ac.il");
	}
	
	@Test
	public void testCreateItem() {
		// GIVEN the server is up
		
			
		// WHEN I invoke POST /twins with {"item":"myTestItem"}
		ItemBoundary item = new ItemBoundary();
		item.setName("myTestItem");
		ItemBoundary reponse = this.restTemplate
			.postForObject(this.url, item, ItemBoundary.class);
		
		
		// THEN the server returns status 2xx
		// AND the response body contains "item":"myTestItem"

		assertThat(reponse.getName())
			.isEqualTo("myTestItem");
	}
	
//	@Test
//	public void testUpdateItem() {
//		// GIVEN the server is up
//
//	}
//	
//	@Test
//	public void testRetrieveItem() {
//		// GIVEN the server is up
//
//	}
//	
//	@Test
//	public void testRetrieveAllItem() {
//		// GIVEN the server is up
//	
//
//	}
	
}

