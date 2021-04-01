package twins.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import twins.additionalClasses.ItemId;
import twins.boundaries.ItemBoundary;
import twins.boundaries.UserBoundary;
import twins.dal.ItemHandler;
import twins.data.ItemEntity;
import twins.data.UserEntity;

/* 
------------------------------------------------------------
in this class we need to check :
 
1. items are not null
2. items type is not null
3. we need to create id's for new item that is created
--------------------------------------------------------------
*/

@Service
public class ItemsServiceImplement implements ItemsService {

	private ItemHandler itemHandler;

	public ItemsServiceImplement(ItemHandler itemHandler) {
		super();
		this.itemHandler = itemHandler;
	}

	@Override
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) {
		if (item != null) {
			item.setItemID(new ItemId(userSpace, userEmail)); // TODO check if ItemId needs ID as integer or userEmail
																// // is okay !!!
			ItemEntity itemEntity = new ItemEntity(item.getItemID(), item.getType(), item.getName(), item.isActive(),
					item.getCreatedTimestamp(), item.getCreatedBy(), item.getLocation(), item.getItemAttributes());
			this.itemHandler.save(itemEntity);

		} else
			System.out.println("Item is not Initialized");
		return item;
	}

	@Override
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		Optional<ItemEntity> itemEntity = this.itemHandler.findById(itemId);
		if (itemEntity != null) {
			// we need to ask eyal what to do
		}
		return update;
	}

	@Override
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail) {
		Iterable<ItemEntity> allEntities = this.itemHandler.findAll();
		List<ItemBoundary> itemBoundaries = new ArrayList<>();
		for (ItemEntity itemEntity : allEntities) {
			ItemBoundary itemBoundary = new ItemBoundary(itemEntity.getItemID().getSpace(),
					itemEntity.getItemID().getID(),
					itemEntity.getType(),
					itemEntity.getName(),
					itemEntity.isActive(),
					itemEntity.getCreatedBy(),
					itemEntity.getLocation());

			itemBoundaries.add(itemBoundary);
		}
		return itemBoundaries;
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllItems(String adminSpace, String adminEmail) {
		// TODO Auto-generated method stub

	}

}
