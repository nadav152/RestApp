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
	/*
	 * need to fix userid string to contain the space and email.
	 * last failure happened at handler.save.
	 */
	//private String userID;
	private String Space;
	private String Email;
	private String role;
	private String username;
	private String avatar;
	
	
	public UserEntity() {
		
	}
	/*	
	@Id
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(UserId userId) {
		this.userID = userId.getSpace()+ "|" +userId.getEmail();
	}
	*/
	@Id
	public String getEmail() {
		return this.Email;
	}
	
	public void setEmail(String email) {
		this.Email = email;
	}
	
	public String getSpace() {
		return this.Space;
	}
	
	public void setSpace(String space) {
		this.Space = space;
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
