package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//Save this file again

public class ItemBoundary {
	private ItemID itemID;			
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private UserBoundary createdBy;
	private Double[] location;
	private Map<String, String> itemAttributes;
	
	public ItemBoundary() {
		this.createdTimestamp = new Date();
		this.itemAttributes = new HashMap<>();
	}
	
	public ItemBoundary(String space, String id, String type, String name, boolean active, UserBoundary createdBy) {
		this();
		ItemID itemID = new ItemID(space, id);
		this.itemID = itemID;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdBy = createdBy;
	}
	
	public ItemID getItemID() {
		return itemID;
	}

	public void setItemID(ItemID itemID) {
		this.itemID = itemID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public UserBoundary getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}

	public Double[] getLocation() {
		return location;
	}

	public void setLocation(Double[] location) {
		this.location = location;
	}

	public Map<String, String> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	

}
