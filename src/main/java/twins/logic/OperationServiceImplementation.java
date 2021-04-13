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
		for (OperationEntity operation : allEntities) {
			OperationBoundary ob = this.convertToBoundary(operation);
			operationBoundaries.add(ob);
		}
		return operationBoundaries;
	}

	@Override
	@Transactional (readOnly = true)
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		this.operationHandler.deleteAll();
		
	}
	
	//convert boundary -> entity
	private OperationEntity convertToEntity(OperationBoundary operation) {
		OperationEntity entity = new OperationEntity();
		entity.setOperationId(operation.getOperationId().getId());
		entity.setType(operation.getType());
		entity.setItem(operation.getItem());
		entity.setCreatedTimestamp(operation.getCreatedTimestamp());
		entity.setOperationAttributes(this.marshall(operation.getOperationAttributes()));
		
		if (operation.getInvokedBy()!= null) {
			entity.setInvokedBy(new User(operation.getInvokedBy().getUserId().getEmail(),operation.getInvokedBy().getUserId().getSpace()));
		}
		return entity;
	}
	
	//convert entity-> boundary
	private OperationBoundary convertToBoundary(OperationEntity oe) {
		OperationBoundary ob = new OperationBoundary();
		ob.setOperationId(new OperationId(oe.getOperationId(),oe.getOperationSpace()));
		ob.setCreatedTimestamp(oe.getCreatedTimestamp());
		
		String details = oe.getOperationAttributes();
		// use jackson for unmarshalling JSON --> Map
		Map<String, Object> operationAttributesMap = this.unmarshall(details, Map.class);
		
		ob.setOperationAttributes(operationAttributesMap);
		ob.setInvokedBy(new User(oe.getInvokedBy().getUserId().getSpace(),oe.getInvokedBy().getUserId().getEmail()));
		return ob;
	}

	private String marshall(Object value) {
		try {
			return this.jackson
				.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private <T> T unmarshall(String details, Class<T> reqClass) {
		try {
			return this.jackson
				.readValue(details, reqClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
