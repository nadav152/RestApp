package demo;

public class UserID {
	private String space;
	private String email;
	
	
	public UserID() {
		
	}
	
	public UserID(String space, String email) {
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
