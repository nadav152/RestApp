package twins.boundaries;

import twins.additionalClasses.UserId;
import twins.data.UserEntity;
import twins.data.UserRole;


public class UserBoundary {
	private UserId userID;
	private UserRole role;
	private String username;
	private String avatar;

	
	public UserBoundary() {
		
	}

	public UserBoundary(String space, String email, UserRole role, String username, String avatar) {
		this();
		this.userID = new UserId(space, email);
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}
	
	public UserId getUserID() {
		return userID;
	}

	public void setUserID(UserId userID) {
		this.userID = userID;
	}
	public void setUserID(String userSpace, String userEmail) {
		this.userID.setSpace(userSpace);
		this.userID.setEmail(userEmail);
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
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
