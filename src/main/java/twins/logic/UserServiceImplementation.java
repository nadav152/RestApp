package twins.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import twins.data.UserEntity;
import twins.data.UserRole;
import twins.dal.UserHandler;
import twins.additionalClasses.UserId;
import twins.boundaries.UserBoundary;


@Service
public class UserServiceImplementation implements UsersService {
	private UserHandler userHandler;
	private ObjectMapper jackson;
	private String space;
	
	@Autowired	
	public UserServiceImplementation(UserHandler userHandler) {
		super();
		this.userHandler = userHandler;
	}
	
	@Override											
	public UserBoundary createUser(UserBoundary boundary) {
		if (boundary.getUsername()==null) {
			throw new RuntimeException("UserName attribute must not be null");
		}
		if (boundary.getAvatar()==null) {
			throw new RuntimeException("Avatar attribute must not be null");
		}
		if (boundary.getAvatar()=="") {
			throw new RuntimeException("Avatar attribute must not be an empty string");
		}
		if (!(boundary.getUserID().getEmail().contains("@"))) {
			throw new RuntimeException("Email attribute is not valid");
		}
		UserEntity entity = this.convertToEntity(boundary);
		entity.getUserID().setSpace(space);
		
		entity = this.userHandler.save(entity);
		
		return this.convertToBoundary(entity);
	}

	@Override											//maybe required catching the exception
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> ue = this.userHandler.findById(userEmail); 
		UserBoundary ub = new UserBoundary();
		if (ue.isPresent()) {
			ub.setUserID(new UserId(userSpace,userEmail));
			ub.setRole(this.unmarshall(ue.get().getRole(), UserRole.class));
			ub.setUserName(ue.get().getUsername());
			ub.setAvatar(ue.get().getAvatar());
		}
		else {
			throw new RuntimeException("user could not be found");
		}
		return ub;
	}

	//not updating user id > take user id from vars not from update.
	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		Optional<UserEntity> oue = this.userHandler.findById(userEmail);
		if (oue.isPresent()) {	//updating existing user.
			update.setUserID(userSpace, userEmail);
			UserEntity updatedEntity = this.convertToEntity(update);	
			this.userHandler.save(updatedEntity);
		}
		else {		//user doesn't exist
			throw new RuntimeException("user could not be found");
		}
		return update;
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Iterable<UserEntity> allEntities = this.userHandler.findAll();
		List<UserBoundary> userBoundaries = new ArrayList<>(); 
		for (UserEntity entity : allEntities) {
			userBoundaries.add(this.convertToBoundary(entity));
		}
		return userBoundaries;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userHandler.deleteAll();
		
	}
	
	private UserEntity convertToEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserID(boundary.getUserID());
		entity.setRole(this.marshall(boundary.getRole()));
		entity.setUsername(boundary.getUsername());
		entity.setAvatar(boundary.getAvatar());

		return entity;
	}
	
	private UserBoundary convertToBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserID(entity.getUserID());
		boundary.setRole(this.unmarshall(entity.getRole(), UserRole.class));
		boundary.setUserName(entity.getUsername());
		boundary.setAvatar(boundary.getAvatar());
		
		return boundary;
	}
	private String marshall(Object value) {
		try {
			return this.jackson
				.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private <T> T unmarshall(String json, Class<T> requiredType) {
		try {
			return this.jackson
				.readValue(json, requiredType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
