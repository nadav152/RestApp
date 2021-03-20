package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationBoundary {
	private OperationID operationId;
	private String type;
	private Item itemId;
	private Date createdTimestamp;
	private User invokedBy;
	private Map<String, String> operationAttributes;

	public OperationBoundary() {
		this.createdTimestamp = new Date();
		this.operationAttributes = new HashMap<>();
	}

	public OperationBoundary(String space, String id, String type, Item item, User invokedBy) {
		this();
		OperationID oID = new OperationID(space, id);
		this.operationId = oID;
		this.type = type;
		this.itemId = item;
		this.invokedBy = invokedBy;
	}

	public OperationBoundary(String space, String id, OperationBoundary ob) {
		this();
		OperationID oID = new OperationID(space, id);
		this.operationId = oID;
		this.type = ob.getType();
		this.itemId = ob.getItem();
		this.invokedBy = ob.getInvokedBy();
		this.createdTimestamp = ob.getCreatedTimestamp();
	}

	public OperationID getOperationId() {
		return operationId;
	}

	public void setOperationId(OperationID operationId) {
		this.operationId = operationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Item getItem() {
		return itemId;
	}

	public void setItem(Item item) {
		this.itemId = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public User getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(User invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, String> getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(Map<String, String> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

	@Override
	public String toString() {
		return "OperationBoundary [operationId=" + operationId + ", type=" + type + ", item=" + itemId
				+ ", createdTimestamp=" + createdTimestamp + ", invokedBy=" + invokedBy + ", operationAttributes="
				+ operationAttributes + "]";
	}

}
