package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		ItemID itemID = new ItemID(space, id);
		this.itemID = itemID;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdBy = createdBy;
	}

}
