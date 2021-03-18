package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
	public UserBoundary createUser (NewUserDetails ud){
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
		OperationBoundary[] operations = {new OperationBoundary("Sector 12", "11", "operationType", "item", ub, null),
										new OperationBoundary("Sector 12", "12", "operationType2", "item2", ub, null)};
		
		return operations;
	}
	
}
