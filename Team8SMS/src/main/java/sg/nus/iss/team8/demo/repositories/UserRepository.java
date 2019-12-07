
package sg.nus.iss.team8.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.User;
import sg.nus.iss.team8.demo.models.UserSession;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
 
	
	 @Query("select u from User u where u.username= :name")
	 User findByUsername(@Param("name")String name);
	
	 
	 @Query("Select u.userType from User u where u.username =:name")
	 String findUserType(@Param("name")String name);
}
