 package sg.nus.iss.team8.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import sg.nus.iss.team8.demo.models.User;


@Service
@Repository
public interface UserService {


	User findUser(String name);

  //   boolean isValid(String password, User user) ;
     String findUserType(String name);

}
