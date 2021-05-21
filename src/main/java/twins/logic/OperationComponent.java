package twins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twins.boundaries.OperationBoundary;
import twins.dal.OperationHandler;

@Component
public class OperationComponent {
	private OperationBoundary operationBoundary;
	private OperationHandler operationHandler;
	
	public OperationComponent(OperationBoundary operationBoundary) {
		super();
		this.operationBoundary = operationBoundary;
	}
	
	@Autowired
	public OperationComponent(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
	}

	public void switchCase() {
		String type = this.operationBoundary.getType();
		
		/*switch (type) {
		
		case "Book field":
			if(this.operationBoundary.getOperationAttributes().containsKey("Soccer"))
				reserveSoccerField();
			else if(this.operationBoundary.getOperationAttributes().containsKey("Tennis"))
				reserveTennisCourt();
			else if(this.operationBoundary.getOperationAttributes().containsKey("Basketball"))
				reserveBasketballCourt();
			break;
		
		case "Extra activities":
			if(this.operationBoundary.getOperationAttributes().containsKey("Yoga"))
				signToYoga();
			else if(this.operationBoundary.getOperationAttributes().containsKey("HipHop"))
				signtoHipHop();
		default:
			break;
		}*/
		
	}
	

	
}
