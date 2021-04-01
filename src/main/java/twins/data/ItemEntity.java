package twins.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import twins.additionalClasses.ItemId;
import twins.additionalClasses.Location;
import twins.boundaries.UserBoundary;

/*
ITEMS_TABLE

DATE <PK>      | NAME      | TYPE         |  ACTIVE				(date was chosen for its unique number) 
=====================================================
DATE       | VARCHAR(255)  | VARCHAR(255) | BOOLEAN              //TODO add more attributes if needed
*/

@Entity
@Table(name = "ITEMS_TABLE")
public class ItemEntity {

	private ItemId itemID;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private UserBoundary createdBy;
	private Location location;
	private Map<String, String> itemAttributes;

	public ItemEntity() {
		this.itemID = new ItemId();
		this.createdTimestamp = new Date();
		this.createdBy = new UserBoundary();
		this.location = new Location();
		this.itemAttributes = new HashMap<>();
	}

	public ItemEntity(ItemId itemID, String type, String name, boolean active, Date createdTimestamp,
			UserBoundary createdBy, Location location, Map<String, String> itemAttributes) {
		super();
		this.itemID = itemID;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}

	@Transient
	public ItemId getItemID() {
		return itemID;
	}

	@Transient
	public void setItemID(ItemId itemID) {
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

	@Id
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Transient
	public UserBoundary getCreatedBy() {
		return createdBy;
	}

	@Transient
	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}

	@Transient
	public Location getLocation() {
		return location;
	}

	@Transient
	public void setLocation(Location location) {
		this.location = location;
	}

	@Transient
	public Map<String, String> getItemAttributes() {
		return itemAttributes;
	}

	@Transient
	public void setItemAttributes(Map<String, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
}
