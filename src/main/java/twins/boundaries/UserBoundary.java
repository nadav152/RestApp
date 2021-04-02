package twins.boundaries;

import twins.additionalClasses.UserId;
import twins.data.UserEntity;


public class UserBoundary {
	private UserId userID;
	private String role;
	private String username;
	private String avatar;

	
	public UserBoundary() {
		
	}

	public UserBoundary(String space, String email, String role, String username, String avatar) {
		this();
		this.userID = new UserId(space, email);
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}
	public UserBoundary(UserEntity userEntity) {
		this();
		this.userID = userEntity.getUserID();
		this.role = userEntity.getRole();
		this.username = userEntity.getUsername();
		this.avatar = userEntity.getAvatar();
	}
	
	public UserId getUserID() {
		return userID;
	}

	public void setUserID(UserId userID) {
		this.userID = userID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
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
