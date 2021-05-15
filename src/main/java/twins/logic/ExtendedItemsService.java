package twins.logic;

import java.util.List;

import twins.boundaries.ItemBoundary;

public interface ExtendedItemsService extends ItemsService {

	List<ItemBoundary> getAllItems(String userSpace, String userEmail, int page, int size);
}
