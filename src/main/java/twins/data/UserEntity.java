package twins.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import twins.additionalClasses.UserId;

/*
USERS_TABLE

USERNAME <PK>      | AVATAR				(ROLE might be added) 
============================
VARCHAR(255)       | VARCHAR(255)
*/
@Entity
@Table(name="USERS_TABLE")
public class UserEntity {
	private UserId userID;
	private String role;
	private String username;
	private String avatar;
	
	
	public UserEntity() {
		this.userID = new UserId();
	}
	public UserEntity(UserId userID, String role, String username, String avatar) {
		super();
		this.userID = userID;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}
	@Transient
	public UserId getUserID() {
		return userID;
	}
	@Transient
	public void setUserID(UserId userID) {
		this.userID = userID;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Id
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
