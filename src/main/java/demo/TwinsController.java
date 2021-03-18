package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public void createItem (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		// STUB implementation
		//ItemBoundary items
		System.err.println("All users from space " + userSpace + " have been deleted by " + userEmail);
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
