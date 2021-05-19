package twins.logic;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import twins.boundaries.UserBoundary;
import twins.data.UserEntity;

public interface ExtendedUsersService extends UsersService {

	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int size, int page);

	//@Query("select u from User u")
	//UserEntity[] findAllUsersByUserId(Pageable pageable);


}
