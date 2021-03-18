package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationBoundary {
	private OperationID operationID;
	private String type;
	private String item;			/// FIX ItemBoundary
	private Date createdTimestamp;
	private UserBoundary invokedBy;
	private Map<String, String> operationAttributes;

	
	public OperationBoundary() {
		createdTimestamp = new Date();
		this.operationAttributes = new HashMap<>();
	}

	

	public OperationBoundary(String space, String id, String type, String item, UserBoundary invokedBy) {
		this();
		OperationID oID = new OperationID(space, id);
		this.operationID = oID;
		this.type = type;
		this.item = item;
		this.invokedBy = invokedBy;
		
	}
	
	public OperationID getOperationID() {
		return operationID;
	}

	public void setOperationID(OperationID oID) {
		this.operationID = oID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public UserBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(UserBoundary invokedBy) {
		this.invokedBy = invokedBy;
	}
	
	public void setOperationAttributes(Map<String, String> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

	public Map<String, String> getOperationAttributes() {
		return operationAttributes;
	}

	

}
