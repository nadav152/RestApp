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
	private Location location;
	private Map<String, String> itemAttributes;
	
	public ItemBoundary() {
		this.createdTimestamp = new Date();
		this.itemAttributes = new HashMap<>();
	}
	
	public ItemBoundary(String space, String id, String type, String name, boolean active, UserBoundary createdBy, Location location) {
		this();
		this.itemID = new ItemID(space, id);
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdBy = createdBy;
		this.location = location;
	}
	
	public ItemBoundary(String space, String id, ItemBoundary IDlessItem) {
		this();
		this.itemID = new ItemID(space, id);
		this.type = IDlessItem.getType();
		System.out.println(IDlessItem.getType());
		this.name = IDlessItem.getName();
		this.active = IDlessItem.isActive();
		this.createdBy = IDlessItem.getCreatedBy();
		this.location = IDlessItem.getLocation();
		this.createdTimestamp = IDlessItem.getCreatedTimestamp();
	}
	
	@Override
	public String toString() {
		return "ItemBoundary [itemID=" + itemID + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", itemAttributes=" + itemAttributes + "]";
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, String> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	

}
