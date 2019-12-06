 package sg.nus.iss.team8.demo.services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import sg.nus.iss.team8.demo.models.User;


@Service
@Repository
public interface UserService {


	User findUser(String name);

	

}
