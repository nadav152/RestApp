package demo;


public class UserBoundary {
	private UserID userID;
	private String role;
	private String username;
	private String avatar;

	
	public UserBoundary() {
		
	}

	public UserBoundary(String space, String email, String role, String username, String avatar) {
		this();
		this.userID = new UserID(space, email);
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}
	
	public UserID getUserID() {
		return userID;
	}

	public void setUserID(UserID userID) {
		this.userID = userID;
	}

	public String getRole() {
		return role;
	}

	public void Role(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setLastName(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "Member's Details:\n [userID : [" + userID.getSpace() + " , "+ userID.getEmail() + "], role : " + role + ", username : " + username
				+ ", avatar : " + avatar + "]";
	}

}
