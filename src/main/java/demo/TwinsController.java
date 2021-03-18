package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Save this file again

@RestController
public class TwinsController {
	/* Users related API */
	@RequestMapping(
			path="/twins/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser (@RequestBody NewUserDetails ud){
		// STUB implementation
		UserBoundary u = new UserBoundary("sector 12", ud.email, ud.role, ud.username, ud.avatar);
		return u;
	}
	
	@RequestMapping(
			path="/twins/users/login/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginAndRetrieveDetails (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		UserBoundary u = new UserBoundary(userSpace, userEmail, "Member", "EfiRefaelo", "ER");
		return u;
	}
	
	@RequestMapping(
			path="/twins/users/{userSpace}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (UserBoundary ub,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		System.err.println("The user from space " + userSpace + " has been updated by " + userEmail);
	}
	
	
	/* Digital Items related API */
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}",
			method = RequestMethod.POST)
	public ItemBoundary createItem (ItemBoundary IDlessItem,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		UserBoundary u = new UserBoundary(userSpace, userEmail, "Member", "EfiRefaelo", "ER");
		IDlessItem.setItemID(new ItemID(userSpace, "123"));
		return IDlessItem;
	}
	
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemID}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateItem (ItemBoundary ib,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemID") String itemID){
		// STUB implementation
		System.err.println("The item " + itemID + " from space " + itemSpace + " has been updated by " + userEmail + " from space " + userSpace);
	}
	
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemID}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary retrieveItem (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemID") String itemID){
		// STUB implementation
		UserBoundary u = new UserBoundary(userSpace, userEmail, "Member", "EfiRefaelo", "ER");
		ItemBoundary ib = new ItemBoundary(itemSpace, itemID, "food", "cheese", true, u);
		return ib;
	}
	
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] retrieveAllItem (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		UserBoundary u = new UserBoundary(userSpace, userEmail, "Member", "EfiRefaelo", "ER");
		ItemBoundary[] allItems = {new ItemBoundary("Pool", "124", "Attraction", "showers", true, u),
									new ItemBoundary("Gym", "125", "Outfit", "Fins", true, u)};
		return allItems;
	}
		
	
	
	
	
	/* Admin API */
	@RequestMapping(
			path="/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteUsers (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		System.err.println("All users from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/items/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteItems (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		System.err.println("All items from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteOperations (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		System.err.println("All Operations from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportUsers (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		UserBoundary[] users = {new UserBoundary("Sector 12", "EfiRefaelo@gmail.com", "Member", "EfiRefaelo", "ER"),
								new UserBoundary("Sector 12", "RoeAvshalom@gmail.com", "Member", "RoeAvshalom", "RA")};
		
		return users;
	}
	
	@RequestMapping(
			path="/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportOperations (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		UserBoundary ub = new UserBoundary("Sector 12", "EfiRefaelo@gmail.com", "Member", "EfiRefaelo", "ER");
		OperationBoundary ob1 = new OperationBoundary("Sector 12", "11", "operationType", "item", ub);
		OperationBoundary ob2 = new OperationBoundary("Sector 12", "12", "operationType2", "item2", ub);
		ob1.getOperationAttributes().put("key1", "table");
		ob1.getOperationAttributes().put("key2", "desk");
		ob2.getOperationAttributes().put("key1", "chair");
		ob2.getOperationAttributes().put("key2", "screen");
		OperationBoundary[] operations = {ob1, ob2};
		
		
		return operations;
	}
	
}
