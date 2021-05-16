package twins;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import twins.additionalClasses.Item;
import twins.additionalClasses.OperationId;
import twins.additionalClasses.User;
import twins.boundaries.OperationBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OperationTests {

	
	/*
	 * 
	 *  This class is for Operation URL HTML test 
	 * 
	 */
	
	private int port;
	private String url; // http://localhost:port/twins/operations
	private RestTemplate restTemplate;
	
	@LocalServerPort // inject port number to test case
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/twins/operations";
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
	public void TestInvokeOperatiton() throws Exception{
		OperationBoundary ob = new OperationBoundary();
		ob.setType("check");
		ob.setItem(new Item("Pool", "124"));
		ob.setOperationId(new OperationId("Sector 12", "de938525-f579-4e33-ae40-1def64cb4bdb"));
		//ob.setOperationAttributes(Collections.singletonMap("test", "demo"));
		ob.setInvokedBy(new User("Sector 12", "EfiRefaelo@gmail.com"));
		//ob.setCreatedTimestamp(new Date());
		OperationBoundary response = this.restTemplate
				.postForObject(this.url, ob, OperationBoundary.class);
		assertThat(response.getType())
			.isEqualTo("check");
		System.err.println("***"+response);
	}
	
	@Test
	public void TestInvokeAsynchorniousOperation() throws Exception{
		OperationBoundary ob = new OperationBoundary();
		ob.setType("check");
		ob.setItem(new Item("Pool", "124"));
		ob.setOperationId(new OperationId("Sector 12", "de938525-f579-4e33-ae40-1def64cb4bdb"));
		ob.setInvokedBy(new User("Sector 12", "EfiRefaelo@gmail.com"));
		OperationBoundary response = this.restTemplate
				.postForObject(this.url + "/async", ob, OperationBoundary.class);
		assertThat(response.getType())
			.isEqualTo("check");
		System.err.println("***"+response);

	}
	
}
