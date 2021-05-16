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


import twins.boundaries.OperationBoundary;
import twins.logic.OperationsService;

// Save this file again
/* LAST UPDATE: 22/4/21 19:00 p.m
- Added a Constructor for the services.  
- Added class OperationTest.
- Added one test invokedOperation(doesn't work at the moment).
- 
*/


@RestController
public class OperationController {
	private OperationsService operationsService;
	
	
	@Autowired
	public OperationController(OperationsService operationsService) {
		this.operationsService = operationsService;
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
			path="/twins/admin/operations/{userSpace}/{userEmail}",
			method = RequestMethod.DELETE)
	public void deleteOperations (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail){
		this.operationsService.deleteAllOperations(userSpace, userEmail);
		System.err.println("All Operations from space " + userSpace + " have been deleted by " + userEmail);
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