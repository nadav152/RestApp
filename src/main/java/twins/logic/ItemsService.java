package twins.logic;

import java.util.List;

import twins.boundaries.ItemBoundary;

public interface ItemsService {
	ItemBoundary createItem(String userSpace, String userEmail,
							ItemBoundary item);
	ItemBoundary updateItem(String userSpace, String userEmail,
							String itemSpace, String itemId,
							ItemBoundary update);
	List<ItemBoundary> getAllItems(String userSpace, String userEmail);
	ItemBoundary getSpecificItem(String userSpace, String userEmail,
										String itemSpace, String itemId);
	void deleteAllItems(String adminSpace, String adminEmail);
}
