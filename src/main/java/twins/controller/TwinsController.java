package twins.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.additionalClasses.Item;
import twins.additionalClasses.Location;
import twins.additionalClasses.NewUserDetails;
import twins.additionalClasses.User;
import twins.boundaries.ItemBoundary;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.data.UserRole;
import twins.logic.ItemsService;
import twins.logic.OperationsService;
import twins.logic.UsersService;

// Save this file again

@RestController
public class TwinsController {
	private UsersService usersService;
	private ItemsService itemsService;
	private OperationsService operationsService;
	
	/* Users related API */
	@RequestMapping(
			path="/twins/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser (@RequestBody NewUserDetails ud){
		// STUB implementation
		UserBoundary u = new UserBoundary("sector 12", ud.getEmail(), ud.getRole(), ud.getUsername(), ud.getAvatar());
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
		UserBoundary u = new UserBoundary(userSpace, userEmail, UserRole.PLAYER, "EfiRefaelo", "ER");
		return u;
	}
	
	@RequestMapping(
			path="/twins/users/{userSpace}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (@RequestBody UserBoundary ub,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		System.err.println("The user from space " + userSpace + " has been updated by " + userEmail);
	}
	
	
	/* Digital Items related API */
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary createItem (@RequestBody ItemBoundary IDlessItem,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		ItemBoundary itemboundary = this.itemsService.createItem(userSpace, userEmail, IDlessItem);
		return itemboundary;
	}
	
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemID}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateItem (@RequestBody ItemBoundary ib,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemID") String itemID){
		
		this.itemsService.updateItem(userSpace, userEmail, itemSpace, itemID, ib);
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
		
		return this.itemsService.getSpecificItem(userSpace, userEmail, itemSpace, itemID);
	}
	
	@RequestMapping(
			path="/twins/items/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] retrieveAllItem (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		List<ItemBoundary> allItems = this.
				itemsService.getAllItems(userSpace, userEmail);
		
		return allItems.toArray(new ItemBoundary[0]);
	}
		
	/* Operations related API*/
	@RequestMapping(
			path="/twins/operations",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeOperation (@RequestBody OperationBoundary operation){
		OperationBoundary ob = new OperationBoundary("stub space","99",operation);
		return ob;
		// STUB implementation
	}
	
	@RequestMapping(
			path="/twins/operations/async",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary syncOperation (@RequestBody OperationBoundary operation){
		// STUB implementation
		OperationBoundary ob = new OperationBoundary("stub space","99",operation);
		
		return ob;
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
		UserBoundary[] users = {new UserBoundary("Sector 12", "EfiRefaelo@gmail.com", UserRole.PLAYER, "EfiRefaelo", "ER"),
								new UserBoundary("Sector 12", "RoeAvshalom@gmail.com", UserRole.PLAYER, "RoeAvshalom", "RA")};
		
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
		User u = new User("Sector 12", "EfiRefaelo@gmail.com");
		Item i = new Item("Pool", "124");
		OperationBoundary ob1 = new OperationBoundary("Sector 12", "11", "operationType", i, u);
		OperationBoundary ob2 = new OperationBoundary("Sector 12", "12", "operationType2", i, u);
		ob1.getOperationAttributes().put("key1", "table");
		ob1.getOperationAttributes().put("key2", "desk");
		ob2.getOperationAttributes().put("key1", "chair");
		ob2.getOperationAttributes().put("key2", "screen");
		OperationBoundary[] operations = {ob1, ob2};
		
		return operations;
	}
	
}