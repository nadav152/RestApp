package twins.dal;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import twins.data.UserEntity;

public interface UserHandler extends PagingAndSortingRepository<UserEntity, String> {
	
	
	
	// C - Create
	
	// R - Read
	
	/*public List<UserEntity> findAllByUserIdLike(@Param("pattern") String pattern,
	Pageable pageable);
*/
	
	// U - Update
	
	// D - Delete

}
