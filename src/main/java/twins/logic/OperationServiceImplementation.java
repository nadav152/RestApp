package twins.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twins.boundaries.OperationBoundary;
import twins.dal.OperationHandler;
import twins.data.OperationEntity;

@Service
public class OperationServiceImplementation implements OperationsService {

	private OperationHandler operationHandler;
	
	@Autowired
	public OperationServiceImplementation(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
	}

	@Override					//need to check operationId new values(currently unchanged), createdtimestamp
	public Object invokeOperation(OperationBoundary operation) {
		OperationEntity oe = new OperationEntity(operation.getOperationId(), operation.getType(), operation.getItem(), new Date(),
				operation.getInvokedBy(), operation.getOperationAttributes());
		this.operationHandler.save(oe);
		OperationBoundary ob = new OperationBoundary(oe);
		return ob;
	}

	@Override					//need to check operationId new values(currently unchanged), createdtimestamp
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) { 
		OperationEntity oe = new OperationEntity(operation.getOperationId(), operation.getType(), operation.getItem(), new Date(),
				operation.getInvokedBy(), operation.getOperationAttributes());
		this.operationHandler.save(oe);
		OperationBoundary ob = new OperationBoundary(oe);
		return ob;
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		Iterable<OperationEntity> allEntities = this.operationHandler.findAll();
		List<OperationBoundary> operationBoundaries = new ArrayList<>(); 
		for (OperationEntity operation : allEntities) {
			OperationBoundary ob = new OperationBoundary();
			ob.setOperationId(operation.getOperationId());			
			ob.setType(operation.getType());
			ob.setItem(operation.getItem());
			ob.setCreatedTimestamp(operation.getCreatedTimestamp());
			ob.setInvokedBy(operation.getInvokedBy());
			ob.setOperationAttributes(operation.getOperationAttributes());
			operationBoundaries.add(ob);
		}
		return operationBoundaries;
	}

	@Override
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		this.operationHandler.deleteAll();
		
	}

}
