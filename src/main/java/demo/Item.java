package demo;

public class Item {
	private ItemID itemId;

	public Item() {
			
	}
	
	public Item(String space, String id) {
		this();
		this.itemId.setSpace(space);
		this.itemId.setID(id);
	}

	public ItemID getItem() {
		return itemId;
	}

	public void setItem(ItemID item) {
		this.itemId = item;
	}
}
