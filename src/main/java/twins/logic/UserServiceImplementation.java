package twins.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.fasterxml.jackson.databind.ObjectMapper;

import twins.data.UserEntity;
import twins.dal.UserHandler;
import twins.additionalClasses.UserId;
import twins.boundaries.UserBoundary;


@Service
public class UserServiceImplementation implements UsersService {
	private UserHandler userHandler;
	//private ObjectMapper jackson;
	private String space;
	
	@Autowired	
	public UserServiceImplementation(UserHandler userHandler) {
		super();
		this.userHandler = userHandler;
	}
	
	@Value("${spring.application.name:2021b.integ}")
	public void setSpace(String space) {
		this.space = space;
	}
	
	@PostConstruct
	public void init() {
		System.err.println("space: " + this.space);
	}
	
	@Override
	@Transactional //(readOnly = false)
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
		entity = this.userHandler.save(entity);
				
		return this.convertToBoundary(entity);
	}

	@Override											//maybe required catching the exception
	@Transactional(readOnly = true)
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> ue = this.userHandler.findById(userEmail); 
		UserBoundary ub = new UserBoundary();
		if (ue.isPresent()) {
			ub.setUserID(new UserId(userSpace,userEmail));
			ub.setRole(ue.get().getRole());
			//System.err.println(ub.getRole() + "in login");
			ub.setUserName(ue.get().getUsername());
			ub.setAvatar(ue.get().getAvatar());
		}
		else {
			throw new RuntimeException("user could not be found");
		}
		return ub;
	}

	@Override
	@Transactional //(readOnly = false)
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
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Iterable<UserEntity> allEntities = this.userHandler.findAll();
		List<UserBoundary> userBoundaries = new ArrayList<>(); 
		for (UserEntity entity : allEntities) {
			userBoundaries.add(this.convertToBoundary(entity));
		}
		return userBoundaries;
	}

	@Override
	@Transactional //(readOnly = false)
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userHandler.deleteAll();
		
	}
	
	private UserEntity convertToEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserID(boundary.getUserID());
		entity.setUserID(new UserId(space, boundary.getUserID().getEmail()));
		entity.setRole(boundary.getRole().toString());
		entity.setUsername(boundary.getUsername());
		entity.setAvatar(boundary.getAvatar());

		return entity;
	}
	
	private UserBoundary convertToBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		String[] tokens = getTokens(entity.getUserID());
		boundary.setUserID(new UserId(tokens[0], tokens[1]));
		boundary.setRole(entity.getRole());
		boundary.setUserName(entity.getUsername());
		boundary.setAvatar(entity.getAvatar());
		
		return boundary;
	}
	
	private String[] getTokens(String userID) {
		String[] tokens = new String[2];
		tokens = userID.split("\\|");
		return tokens;
	}
	
}
