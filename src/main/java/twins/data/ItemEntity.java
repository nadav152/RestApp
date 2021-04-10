package twins.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	private String id;
	private String space;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private UserBoundary createdBy;
	private Location location;
	private String itemAttributes;
	private String countryClub;

	
	public ItemEntity() {

	}

//	public ItemEntity(ItemId itemID, String type, String name, boolean active, Date createdTimestamp,
//			UserBoundary createdBy, Location location, Map<String, String> itemAttributes) {
//		super();
//		this.itemID = itemID;
//		this.type = type;
//		this.name = name;
//		this.active = active;
//		this.createdTimestamp = createdTimestamp;
//		this.createdBy = createdBy;
//		this.location = location;
//		this.itemAttributes = itemAttributes;
//	}

	

	@Id
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
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
	
	public String getCountryClub() {
		return countryClub;
	}

	public void setCountryClub(String countryClub) {
		this.countryClub = countryClub;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MESSAGE_TIMESTAMP") //  set column name: MESSAGE_TIMESTAMP
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

	@Lob
	public String getItemAttributes() {
		return itemAttributes;
	}

	
	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
}
