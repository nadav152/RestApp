package twins.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
/* LAST UPDATE: 22/4/21 19:00 p.m
- Added a Constructor for the services.  
- Added class OperationTest.
- Added one test invokedOperation(doesn't work at the moment).
- 
*/


@RestController
public class TwinsController {
	private UsersService usersService;
	private ItemsService itemsService;
	private OperationsService operationsService;
	
	
	@Autowired
	public TwinsController(UsersService usersService,ItemsService itemsService,OperationsService operationsService) {
		this.usersService = usersService;
		this.itemsService = itemsService;
		this.operationsService = operationsService;
	}
	/* Users related API */
	@RequestMapping(
			path="/twins/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser (@RequestBody NewUserDetails ud){
		
		UserBoundary u = this.usersService.createUser(new UserBoundary("sector 12", ud.getEmail(), ud.getRole(), ud.getUsername(), ud.getAvatar()));
		return u;
	}
	
	@RequestMapping(
			path="/twins/users/login/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginAndRetrieveDetails (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		
		UserBoundary u = this.usersService.login(userSpace, userEmail);
		return u;
	}
	
	@RequestMapping(
			path="/twins/users/{userSpace}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (@RequestBody UserBoundary ub,
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		this.usersService.updateUser(userSpace, userEmail, ub);
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
		return this.operationsService.invokeOperation(operation);
		
	}
	
	@RequestMapping(
			path="/twins/operations/async",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary syncOperation (@RequestBody OperationBoundary operation){
		return this.operationsService.invokeAsynchronousOperation(operation);
	}
	
	
	/* Admin API */
	@RequestMapping(
			path="/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteUsers (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		this.usersService.deleteAllUsers(userSpace, userEmail);
		System.err.println("All users from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/items/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteItems (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		this.itemsService.deleteAllItems(userSpace, userEmail);
		System.err.println("All items from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteOperations (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		this.operationsService.deleteAllOperations(userSpace, userEmail);
		System.err.println("All Operations from space " + userSpace + " have been deleted by " + userEmail);
	}
	
	@RequestMapping(
			path="/twins/admin/users/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportUsers (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		
		List <UserBoundary> usersList = this.usersService.getAllUsers(userSpace, userEmail);
		UserBoundary[] usersArr = usersList.toArray(new UserBoundary[0]);
		return usersArr;
	}
	
	@RequestMapping(
			path="/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportOperations (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		
		List<OperationBoundary> allOp = this.operationsService.getAllOperations(userSpace, userEmail);
		
		return allOp.toArray(new OperationBoundary[0]);
	}
	
}