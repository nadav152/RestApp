package twins.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import twins.additionalClasses.NewUserDetails;
import twins.boundaries.UserBoundary;
import twins.logic.UsersService;

// Save this file again
/* LAST UPDATE: 22/4/21 19:00 p.m
- Added a Constructor for the services.  
- Added class OperationTest.
- Added one test invokedOperation(doesn't work at the moment).
- 
*/


@RestController
public class UserController {
	private UsersService usersService;	
	
	@Autowired
	public UserController(UsersService usersService) {
		this.usersService = usersService;
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
	
}