package twins;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import twins.additionalClasses.ItemId;
import twins.additionalClasses.Location;
import twins.additionalClasses.NewUserDetails;
import twins.additionalClasses.UserId;
import twins.boundaries.ItemBoundary;
import twins.boundaries.UserBoundary;
import twins.data.UserRole;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemTests {

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
		this.url = "http://localhost:" + this.port + "/twins";
		System.err.println(this.url);
		this.restTemplate = new RestTemplate();
	}

	@AfterEach
	public void tearDown() {
		/// clean database
//		this.restTemplate.delete(this.url);
//			.delete(this.url + "/{space}/{email}", "myspace", "admin@afeka.ac.il");
	}

	@Test
	public void testCreateItem() {
		// GIVEN the server is up
		// new UserBoundary("sector 12", ud.getEmail(), ud.getRole(), ud.getUsername(),
		// ud.getAvatar()
		NewUserDetails newUserDetails = new NewUserDetails("ben@gmail.com", UserRole.MANAGER, "ben", "wolf");
		UserBoundary userResponse = this.restTemplate.postForObject(url + "/users", newUserDetails, UserBoundary.class);
		assertThat(userResponse.getRole().toString().equals("Manager"));

		// WHEN I invoke POST /twins with {"item":"myTestItem"}
		ItemBoundary item = new ItemBoundary("towel", "ben", true,
				new UserId("2021b.Daniel.Aizenband", "ben@gmail.com"), new Location(1.0, 1.0));

		ItemBoundary reponse = this.restTemplate.postForObject(this.url + "/items/2021b.Daniel.Aizenband/ben@gmail.com",
				item, ItemBoundary.class);

		// THEN the server returns status 2xx
		// AND the response body contains "item":"myTestItem"

		assertThat(reponse.getName()).isEqualTo("ben");
	}

	@Test
	public void testUpdateItem() {
		// creating user
		NewUserDetails newUserDetails = new NewUserDetails("ben@gmail.com", UserRole.MANAGER, "ben", "wolf");
		UserBoundary userResponse = this.restTemplate.postForObject(url + "/users", newUserDetails, UserBoundary.class);
		assertThat(userResponse.getRole().toString().equals("Manager"));

		// creating Item boundary
		ItemBoundary itemToBeReplaced = new ItemBoundary("towel", "ben", true,
				new UserId("2021b.Daniel.Aizenband", "ben@gmail.com"), new Location(1.0, 1.0));

		// this post method - ensure the creator user is a manager
		ItemBoundary reponse = this.restTemplate.postForObject(this.url + "/items/2021b.Daniel.Aizenband/ben@gmail.com",
				itemToBeReplaced, ItemBoundary.class);

		ItemBoundary itemToBePlaced = new ItemBoundary("pool", "moshe", true,
				new UserId("2021b.Daniel.Aizenband", "ben123123@gmail.com"), new Location(2.0, 2.0));

		// update item
		/// twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemID}
		this.restTemplate.put(
				this.url + "/items/" + userResponse.getUserId().getSpace() + "/" + userResponse.getUserId().getEmail()
						+ "/" + reponse.getItemID().getSpace() + "/" + reponse.getItemID().getID(),
				itemToBePlaced);

		// get updated Item from DB
		/// twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemID}
		ItemBoundary finalUpdatedItem = this.restTemplate.getForObject(
				this.url + "/items/" + userResponse.getUserId().getSpace() + "/" + userResponse.getUserId().getEmail()
						+ "/" + reponse.getItemID().getSpace() + "/" + reponse.getItemID().getID(),
				ItemBoundary.class);

		assertThat(finalUpdatedItem.getType()).isEqualTo("pool");
	}

	@Test
	public void testRetrieveItem() {
		// this was tested on the test "testUpdateItem()"
	}

	@Test
	public void testRetrieveAllItem() {

		/* This is a paging test
		 * we make 7 items - page size is 5
		 * 
		 * page 0 should have 5 items 
		 * page 1 should have 2 items
		 */
		
		
		// creating user with Role = MANAGER
		NewUserDetails newUserDetails = new NewUserDetails("ben@gmail.com", UserRole.MANAGER, "ben", "wolf");
		UserBoundary userResponse = this.restTemplate.postForObject(url + "/users", newUserDetails, UserBoundary.class);
		assertThat(userResponse.getRole().toString().equals("Manager"));

		//creating 7 item with the MANAGER user
		createItem("towel1", "ben");
		createItem("swimming glasses", "dodo");
		createItem("keys", "popo");
		createItem("golf cart", "momo");
		createItem("towel3", "bobo");
		createItem("towel2", "gogo");
		createItem("towel4", "bebe");
		
		//Using the getAll rest GET method to bring pages of items
		ItemBoundary itemsPage0[] = this.restTemplate.getForObject(url + "/items/2021b.Daniel.Aizenband/ben123123@gmail.com?size=5&page=0",
				ItemBoundary[].class);
		ItemBoundary itemsPage1[] = this.restTemplate.getForObject(url + "/items/2021b.Daniel.Aizenband/ben123123@gmail.com?size=5&page=1",
				ItemBoundary[].class);
		
		
		assertThat(itemsPage0.length).isEqualTo(5);
		assertThat(itemsPage1.length).isEqualTo(2);

	}

	public void createItem(String type, String name) {
		// creating Item boundary
		ItemBoundary itemBoundary = new ItemBoundary(type, name, true,
				new UserId("2021b.Daniel.Aizenband", "ben@gmail.com"), new Location(1.0, 1.0));

		// this post method - ensure the creator user is a manager
		this.restTemplate.postForObject(this.url + "/items/2021b.Daniel.Aizenband/ben@gmail.com",
				itemBoundary, ItemBoundary.class);
	}

}
