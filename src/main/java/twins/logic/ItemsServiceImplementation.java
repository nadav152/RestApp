package twins.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import twins.additionalClasses.ItemId;
import twins.additionalClasses.Location;
import twins.additionalClasses.UserId;
import twins.boundaries.ItemBoundary;
import twins.dal.ItemHandler;
import twins.data.ItemEntity;

@Service
public class ItemsServiceImplementation implements ItemsService {

	private ItemHandler itemHandler;
	private ObjectMapper jackson;
	private String space;

	@Autowired
	public ItemsServiceImplementation(ItemHandler itemHandler) {
		super();
		this.itemHandler = itemHandler;
		this.jackson = new ObjectMapper();
	}

	// have spring initialize the dummy value using property:
	// spring.application.name
	// or generate default value if property does not exist: "dummyValue"
	@Value("${spring.application.name:countryClubValue}")
	public void setDummy(String space) {
		this.space = space;
	}

	// have spring invoke this operation after initializing Spring bean
	@PostConstruct
	public void init() {
		System.err.println("dummy: " + this.space);
	}

	@Override
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) {
		// 1. validate input - make sure message is not null
		if (item == null || item.getType() == null) 
			throw new RuntimeException("Item and item type must not be null");
		
		if (item.getName() == null || item.getName().equals("")) 
			throw new RuntimeException("Item name must not be null or empty");
		
		item.setCreatedBy(new UserId(userSpace, userEmail));
		
		// 2. boundary -> entity
		ItemEntity entity = this.convertToEntity(item);

		// 3. generate ID + timestamp
		entity.setId(UUID.randomUUID().toString());
		entity.setCreatedTimestamp(new Date());

		// 4. set dummy to a constant of the project
		entity.setSpace(this.space);

		// 5. INSERT to database
		entity = this.itemHandler.save(entity);

		// 6. entity -> boundary
		return this.convertToBoundary(entity);
	}

	@Override
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		Optional<ItemEntity> existing = this.itemHandler.findById(itemId);
		if (existing.isPresent()) {
			update.setItemID(new ItemId(existing.get().getSpace(), existing.get().getId()));
			update.setCreatedBy(this.unmarshall(existing.get().getCreatedBy(), UserId.class));
			update.setCreatedTimestamp(existing.get().getCreatedTimestamp());
			ItemEntity updatedEntity = this.convertToEntity(update);

			// UPDATE
			this.itemHandler.save(updatedEntity);
		} else {
			throw new RuntimeException("Item could not be found");
		}

		return update;
	}

	@Override
	@Transactional(readOnly = true) // handle race condition
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail) {
		// BEGIN new tx (transaction)
		Iterable<ItemEntity> allEntities = this.itemHandler.findAll();

		List<ItemBoundary> rv = new ArrayList<>();
		for (ItemEntity entity : allEntities) {
			ItemBoundary boundary = this.convertToBoundary(entity);
	
//			if (boundary.getCreatedBy().getSpace().equals(userSpace)			TODO we might use this later on
//					&& boundary.getCreatedBy().getEmail().equals(userEmail))
				rv.add(boundary);
		}
		return rv;
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		Optional<ItemEntity> existing = this.itemHandler.findById(itemId);
		if (existing.isPresent()) {
			ItemEntity entity = existing.get();
			return this.convertToBoundary(entity);
		} else {
			throw new RuntimeException("Item could not be found");
		}
		// TODO find out where to use user email and space
	}

	@Override
	public void deleteAllItems(String adminSpace, String adminEmail) {
		this.itemHandler.deleteAll();
		// TODO find out how to use admin space and email
	}

	private ItemBoundary convertToBoundary(ItemEntity entity) {
		ItemBoundary boundary = new ItemBoundary();
		boundary.setItemID(new ItemId(entity.getSpace(), entity.getId()));
		boundary.setType(entity.getType());
		boundary.setName(entity.getName());
		boundary.setActive(entity.isActive());
		boundary.setCreatedBy(this.unmarshall(entity.getCreatedBy(), UserId.class));
		boundary.setLocation(this.unmarshall(entity.getLocation(), Location.class));
		
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		String details = entity.getItemAttributes();
		// use jackson for unmarshalling JSON --> Map
		Map<String, Object> moreDetailsMap = this.unmarshall(details, Map.class);
		boundary.setItemAttributes(moreDetailsMap);
		return boundary;
	}

	private ItemEntity convertToEntity(ItemBoundary boundary) {
		ItemEntity entity = new ItemEntity();
		entity.setId(boundary.getItemID().getID());
		entity.setType(boundary.getType());
		entity.setName(boundary.getName());
		entity.setActive(boundary.isActive());
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		entity.setCreatedBy(this.marshall(boundary.getCreatedBy()));
		entity.setLocation(this.marshall(boundary.getLocation()));
		// marshalling of Map to JSON (returned as String)
		entity.setItemAttributes(this.marshall(boundary.getItemAttributes()));
		return entity;
	}

	private <T> T unmarshall(String json, Class<T> requiredType) {
		try {
			return this.jackson.readValue(json, requiredType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String marshall(Object value) {
		try {
			return this.jackson.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
