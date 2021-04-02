package twins.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twins.data.UserEntity;
import twins.dal.UserHandler;
import twins.additionalClasses.UserId;
import twins.boundaries.UserBoundary;


@Service
public class UserServiceImplementation implements UsersService {
	private UserHandler userHandler;
	
	@Autowired	
	public UserServiceImplementation(UserHandler userHandler) {
		super();
		this.userHandler = userHandler;
	}
	
	@Override											
	public UserBoundary createUser(UserBoundary user) {
		UserEntity ue = new UserEntity(user.getUserID(), user.getRole(), user.getUsername(), user.getAvatar());
		this.userHandler.save(ue);
		UserBoundary ub = new UserBoundary(ue);
		return ub;
	}

	@Override											//maybe required catching the exception
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> ue = this.userHandler.findById(userEmail); 
		UserBoundary ub = new UserBoundary();
		if (ue != null) {
			ub.setUserID(new UserId(userSpace,userEmail));
			ub.setRole(ue.get().getRole());
			ub.setUserName(ue.get().getUsername());
			ub.setAvatar(ue.get().getAvatar());
		}
		else {
			throw new RuntimeException();
		}
		return ub;
	}

	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		Optional<UserEntity> oue = this.userHandler.findById(userEmail);
		UserBoundary ub;
		if (oue==null) {	//user doesn't exist, create new one.
			UserEntity ue = new UserEntity(new UserId(userSpace, userEmail), update.getRole(), update.getUsername(), update.getAvatar());
			this.userHandler.save(ue);
			ub = new UserBoundary(ue);
		}
		else {		//updating existing user.
			oue.get().setRole(update.getRole());
			oue.get().setUsername(update.getUsername());
			oue.get().setAvatar(update.getAvatar());	
			ub = new UserBoundary(oue.get());
		}
		return ub;
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Iterable<UserEntity> allEntities = this.userHandler.findAll();
		List<UserBoundary> userBoundaries = new ArrayList<>(); 
		for (UserEntity user : allEntities) {
			UserBoundary ub = new UserBoundary();
			ub.setUserID(user.getUserID());
			ub.setUserName(user.getUsername());
			ub.setAvatar(user.getAvatar());
			ub.setRole(user.getRole());			

			userBoundaries.add(ub);
		}
		return userBoundaries;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userHandler.deleteAll();
		
	}

}
