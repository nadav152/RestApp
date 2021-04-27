package twins.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import twins.additionalClasses.UserId;

/*
USERS_TABLE

      
USERID <PK>  | ROLE          | USERNAME        | AVATAR         
===========================================================
VARCHAR(255) | VARCHAR(255)  | VARCHAR(255)    |  VARCHAR(255)     
*/

@Entity
@Table(name="USERS_TABLE")
public class UserEntity {
	
	private String userID;
	private String role;
	private String username;
	private String avatar;
	
	
	public UserEntity() {
		
	}
		
	@Id
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	@Transient
	public void setUserID(UserId userId) {
		this.userID = userId.getSpace()+ "|" +userId.getEmail();
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
