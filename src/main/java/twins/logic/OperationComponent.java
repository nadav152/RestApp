package twins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twins.dal.OperationHandler;

@Component
public class OperationComponent {
	private String operationType;
	
	

	private OperationHandler operationHandler;

	@Autowired
	public OperationComponent(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
	}
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
}
