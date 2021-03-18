package demo;

public class OperationID {
	private String space;
	private String email;
	
	
	public OperationID() {
		
	}
	
	public OperationID(String space, String email) {
		super();
		this.space = space;
		this.email = email;
	}
	
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
