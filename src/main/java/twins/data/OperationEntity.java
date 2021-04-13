package twins.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	private OperationId opId;
	private String operationId;
	private String operationSpace;
	private String type;
	private Item item;
	private Date createdTimestamp;
	private User invokedBy;
	private String operationAttributes;
	private String groupName;
	
	
	
	public OperationEntity() {
		this.item = new Item();
		this.createdTimestamp = new Date();
		this.invokedBy = new User();
		this.opId = new OperationId();
		
	}
	
	public OperationEntity(String operationId, String type, Item item, Date createdTimestamp, User invokedBy,
			String operationAttributes) {
		this();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}

	@Id
	public String getOperationId() {
		return opId.getId();
	}
	
	public void setOperationId(String operationId) {
		this.operationId = opId.getId();
	}
	@Transient
	public String getOperationSpace() {
		return opId.getSpace();
	}
	@Transient
	public void setOperationSpace(String operationSpace) {
		this.operationSpace = opId.getSpace();
	}
	
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MESSAGE_TIMESTAMP")
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
	public String getOperationAttributes() {
		return operationAttributes;
	}
	@Transient
	public void setOperationAttributes(String operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
	@Transient
	public String getGroupName() {
		return groupName;
	}
	@Transient
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	
	
}
