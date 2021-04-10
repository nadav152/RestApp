package twins.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import twins.additionalClasses.Item;
import twins.additionalClasses.OperationId;
import twins.additionalClasses.User;

/*
OPERATION_TABLE

OPERATION_ID <PK>      | NAME      	   | TYPE         				(date was chosen for its unique number) 
======================================================
OPERATION_ID	       | VARCHAR(255)  | VARCHAR(255)              //TODO add more attributes if needed
*/



@Entity
@Table(name = "OPERATION_TABLE")
public class OperationEntity {
	private OperationId operationId;
	private String type;
	private Item item;
	private Date createdTimestamp;
	private User invokedBy;
	private Map<String, Object> operationAttributes;
	
	
	
	public OperationEntity() {
		this.operationId = new OperationId();
		this.item = new Item();
		this.createdTimestamp = new Date();
		this.invokedBy = new User();
		this.operationAttributes = new HashMap<>();
	}
	
	public OperationEntity(OperationId operationId, String type, Item item, Date createdTimestamp, User invokedBy,
			Map<String, Object> operationAttributes) {
		this();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}

	@Transient
	public OperationId getOperationId() {
		return operationId;
	}
	
	public void setOperationId(OperationId operationId) {
		this.operationId = operationId;
	}
	@Id
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public Item getItem() {
		return item;
	}
	@Transient
	public void setItem(Item item) {
		this.item = item;
	}
	@Transient
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	@Transient
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	@Transient
	public User getInvokedBy() {
		return invokedBy;
	}
	@Transient
	public void setInvokedBy(User invokedBy) {
		this.invokedBy = invokedBy;
	}
	@Transient
	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}
	@Transient
	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
	
	
}
