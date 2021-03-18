package demo;

public class ItemID {
	private String space;
	private String id;
	
	
	public ItemID() {
		
	}
	
	public ItemID(String space, String id) {
		super();
		this.space = space;
		this.id = id;
	}
	
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
}
