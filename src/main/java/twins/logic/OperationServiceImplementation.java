package twins.logic;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import twins.additionalClasses.OperationId;
import twins.additionalClasses.User;
import twins.boundaries.OperationBoundary;
import twins.dal.OperationHandler;
import twins.data.OperationEntity;
import twins.additionalClasses.Item;

@Service
public class OperationServiceImplementation implements OperationsService {

	private OperationHandler operationHandler;
	private ObjectMapper jackson;
	private String space;
	
	@Autowired
	public OperationServiceImplementation(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
		this.jackson = new ObjectMapper();
		
	}
	// have spring initialize the groupName value using property: spring.application.name 
	// or generate default value if property does not exist: "2021b.integ"
	@Value("${spring.application.name:2021b.integ}")
	public void Space(String space) {
		this.space = space;
	}
	
	// have spring invoke this operation after initializing Spring bean
	@PostConstruct
	public void init() {
		System.err.println("space: " + this.space);
	}

	//return JSON obj, after creating new id and updating time stamp
	@Override					
	@Transactional
	public OperationBoundary invokeOperation(OperationBoundary operation) {
		
		if(operation.getType() == null)
			throw new RuntimeException("operation Type must not be null");
		if(operation.getItem() == null)
			throw new RuntimeException("operation Item must not be null");
		if(operation.getItem().getItemId() == null)
			throw new RuntimeException("oper ItemId must not be null");
		if(operation.getInvokedBy() == null)
			throw new RuntimeException("operation Item must not be null");
		
		operation.setCreatedTimestamp(new Date());
		OperationEntity oe = this.convertToEntity(operation);
				
		oe = this.operationHandler.save(oe);
		return this.convertToBoundary(oe);
		
	}
	
	

	//return JSON obj, after creating new id and updating time stamp
	@Override
	@Transactional
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) { 
		if(operation.getType() == null)
			throw new RuntimeException("operation Type must not be null");
		if(operation.getItem() == null)
			throw new RuntimeException("operation Item must not be null");
		if(operation.getItem().getItemId() == null)
			throw new RuntimeException("oper ItemId must not be null");
		if(operation.getInvokedBy() == null)
			throw new RuntimeException("operation Item must not be null");
		
		operation.setCreatedTimestamp(new Date());
		OperationEntity oe = this.convertToEntity(operation);
				
		oe = this.operationHandler.save(oe);
		return this.convertToBoundary(oe);
	}

	//Create a list of all operations entities and convert them to boundaries
	@Override
	@Transactional (readOnly = true)
	@Deprecated
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
//		Iterable<OperationEntity> allEntities = this.operationHandler.findAll();
//		List<OperationBoundary> operationBoundaries = new ArrayList<>(); 
//		for (OperationEntity operation : allEntities) {
//			operationBoundaries.add(this.convertToBoundary(operation));
//		}
//		//TODO check about permissions 
//		return operationBoundaries;
		throw new RuntimeException("depracted method");
	}
	 public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail,
			 										int page, int size){
		Page<OperationEntity> entitiesPage = this.operationHandler.findAll(PageRequest.of(page, size, Direction.DESC, "createdTimestamp"));
		
		List<OperationEntity> pagedEntities = entitiesPage.getContent();
		List<OperationBoundary> rv = new ArrayList<>();
		for (OperationEntity operation : pagedEntities) {
			OperationBoundary ob = this.convertToBoundary(operation);
			if(ob.getInvokedBy() != null)
				rv.add(ob);
		}
		return rv;
	 }

	@Override
	@Transactional
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		this.operationHandler.deleteAll();
	}
	
	
	//convert entity-> boundary
	private OperationBoundary convertToBoundary(OperationEntity oe) {
		OperationBoundary ob = new OperationBoundary();
		String [] tokens = getTokens(oe.getOperationId());
		ob.setOperationId(new OperationId(tokens[0],tokens[1]));
		ob.setType(oe.getType());
		ob.setCreatedTimestamp(oe.getCreatedTimestamp());
		ob.setItem(this.unmarshall(oe.getItem(), Item.class));
		String details = oe.getOperationAttributes();
		// use jackson for unmarshalling JSON --> Map
		Map<String, Object> operationAttributesMap = this.unmarshall(details, Map.class);
		ob.setOperationAttributes(operationAttributesMap);
		ob.setInvokedBy(this.unmarshall(oe.getInvokedBy(), User.class));
		return ob;
	}
	
	//convert boundary -> entity
	private OperationEntity convertToEntity(OperationBoundary operation) {
		OperationEntity entity = new OperationEntity();
		operation.getOperationId().setSpace(space);
		operation.getOperationId().setId(UUID.randomUUID().toString());
		entity.setOperationId(operation.getOperationId());
		entity.setType(operation.getType());
		entity.setItem(this.marshall(operation.getItem()));
		entity.setCreatedTimestamp(operation.getCreatedTimestamp());
		entity.setOperationAttributes(this.marshall(operation.getOperationAttributes()));
		
		if (operation.getInvokedBy()!= null) {
			entity.setInvokedBy(this.marshall(operation.getInvokedBy()));
		}
		return entity;
	}

	private String marshall(Object value) {
		try {
			return this.jackson
				.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private <T> T unmarshall(String json, Class<T> requiredType) {
		try {
			return this.jackson.readValue(json, requiredType);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String[] getTokens(String userID) {
		String[] tokens = new String[2];
		tokens = userID.split("\\|");
		//System.err.println(tokens[0] + " " + tokens[1] + "I'm here");
		return tokens;
	}

}
