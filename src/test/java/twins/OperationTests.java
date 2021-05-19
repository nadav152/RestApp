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
import twins.additionalClasses.Location;
import twins.additionalClasses.NewUserDetails;
import twins.additionalClasses.OperationId;
import twins.additionalClasses.User;
import twins.additionalClasses.UserId;
import twins.boundaries.ItemBoundary;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.data.UserRole;

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
		ItemBoundary ib = new ItemBoundary("space","124","Pool","blabla",true, new UserId("space","demo@gmail.com"), new Location(0, 0));
		String urlCreateItem = "http://localhost:" + this.port +"/twins/items/"+ib.getCreatedBy().getSpace()+"/"+ib.getCreatedBy().getEmail();
		ItemBoundary itemRes = this.restTemplate.postForObject(urlCreateItem, ib, ItemBoundary.class);
		NewUserDetails nud = new NewUserDetails("demo@gmail.com" , UserRole.PLAYER, "dani", "abc");
		String urlCreateUser = "http://localhost:" + this.port + "/twins/users";
		UserBoundary userRes =this.restTemplate.postForObject(urlCreateUser, nud, UserBoundary.class);
		ob.setType("check");
		ob.setItem(new Item("Pool", itemRes.getItemID().getID()));
		ob.setOperationId(new OperationId("Sector 12", "de938525-f579-4e33-ae40-1def64cb4bdb"));
		ob.setInvokedBy(new User(userRes.getUserId().getSpace(),userRes.getUserId().getEmail()));
		OperationBoundary response = this.restTemplate
				.postForObject(this.url, ob, OperationBoundary.class);
		assertThat(response.getType())
			.isEqualTo("check");
		System.err.println("***"+response);
	}
	
	@Test
	public void TestInvokeAsynchorniousOperation() throws Exception{
		
		ItemBoundary ib = new ItemBoundary("space","124","Pool","blabla",true, new UserId("space","demo@gmail.com"), new Location(0, 0));
		String urlCreateItem = "http://localhost:" + this.port +"/twins/items/"+ib.getCreatedBy().getSpace()+"/"+ib.getCreatedBy().getEmail();
		ItemBoundary itemRes = this.restTemplate.postForObject(urlCreateItem, ib, ItemBoundary.class);
		
		String urlCreateUser = "http://localhost:" + this.port + "/twins/users";
		//User1 a player who invoked the operations 
		NewUserDetails nud1 = new NewUserDetails("demo@gmail.com" , UserRole.PLAYER, "dani", "abc");
		UserBoundary userRes1 =this.restTemplate.postForObject(urlCreateUser, nud1, UserBoundary.class);
		
		//User2 an admin to check that the operations exists
		NewUserDetails nud2 = new NewUserDetails("demo2@gmail.com" , UserRole.ADMIN, "Noam", "bla");
		UserBoundary userRes2 =this.restTemplate.postForObject(urlCreateUser, nud2, UserBoundary.class);
		
		OperationBoundary ob = new OperationBoundary();
		ob.setType("check");
		ob.setItem(new Item("Pool", itemRes.getItemID().getID()));
		ob.setOperationId(new OperationId("Sector 12", "de938525-f579-4e33-ae40-1def64cb4bdb"));
		ob.setInvokedBy(new User(userRes1.getUserId().getSpace(),userRes1.getUserId().getEmail()));
		
		OperationBoundary immediateResponse = this.restTemplate
				.postForObject(this.url + "/async", ob, OperationBoundary.class);
		
		String urlForExport = "http://localhost:" + this.port +"/twins/admin/operations";
		boolean flag = false;
		while(!flag) {
			try {
				System.err.println("before getforobject\n");
				OperationBoundary boundaryFromDB = this.restTemplate.
						getForObject(urlForExport + "/"
									+ userRes2.getUserId().getSpace()+
									"/" +userRes2.getUserId().getEmail(),
									OperationBoundary.class);
				System.err.println("boundaries after get\n");
				flag = true;
			} catch (Exception e) {
				try {
					Thread.sleep(2000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				flag = false;
			}
		}

		System.err.println("*** DONE! ***\n");

	}
	
}
