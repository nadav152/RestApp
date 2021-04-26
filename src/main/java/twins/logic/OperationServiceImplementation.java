package twins.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import twins.additionalClasses.OperationId;
import twins.additionalClasses.User;
import twins.additionalClasses.UserId;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.dal.OperationHandler;
import twins.data.OperationEntity;
import twins.additionalClasses.Item;

@Service
public class OperationServiceImplementation implements OperationsService {

	private OperationHandler operationHandler;
	private ObjectMapper jackson;
	private String groupName;
	
	@Autowired
	public OperationServiceImplementation(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
		this.jackson = new ObjectMapper();
		
	}
	// have spring initialize the groupName value using property: spring.application.name 
	// or generate default value if property does not exist: "2021b.integ"
	@Value("${spring.application.name:2021b.integ}")
	public void setDummy(String groupName) {
		this.groupName = groupName;
	}
	
	// have spring invoke this operation after initializing Spring bean
	@PostConstruct
	public void init() {
		System.err.println("groupName: " + this.groupName);
	}

	//return JSON obj, after creating new id and updating time stamp
	@Override					
	@Transactional
	public Object invokeOperation(OperationBoundary operation) {
		if(operation.getItem() == null)
			throw new RuntimeException("operation item must not be null");
		
		OperationEntity oe = this.convertToEntity(operation);

		oe.setOperationId(UUID.randomUUID().toString());
		oe.setCreatedTimestamp(new Date());
		oe.setGroupName(this.groupName);
		
		oe = this.operationHandler.save(oe);
		System.err.println(oe);
		return this.convertToBoundary(oe);
	}

	//return JSON obj, after creating new id and updating time stamp
	@Override
	@Transactional
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) { 
		if(operation.getItem() == null)
			throw new RuntimeException("operation item must not be null");
		
		OperationEntity oe = this.convertToEntity(operation);
		
		oe.setOperationId(UUID.randomUUID().toString());
		oe.setCreatedTimestamp(new Date());
		oe.setGroupName(this.groupName);
		
		oe = this.operationHandler.save(oe);
		
		return this.convertToBoundary(oe);
	}

	//Create a list of all operations entities and convert them to boundaries
	@Override
	@Transactional (readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		Iterable<OperationEntity> allEntities = this.operationHandler.findAll();
		List<OperationBoundary> operationBoundaries = new ArrayList<>(); 
		System.err.println("in get all oper");
		for (OperationEntity operation : allEntities) {
			operationBoundaries.add(this.convertToBoundary(operation));
		}
		//TODO check about permissions 
		return operationBoundaries;
	}

	@Override
	@Transactional
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		this.operationHandler.deleteAll();
		
	}
	
	
	//convert entity-> boundary
	private OperationBoundary convertToBoundary(OperationEntity oe) {
		OperationBoundary ob = new OperationBoundary();
		ob.setOperationId(new OperationId(oe.getOperationSpace(),oe.getOperationId()));
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
		entity.setOperationId(operation.getOperationId().getId());
		entity.setOperationSpace(operation.getOperationId().getSpace());
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

}
