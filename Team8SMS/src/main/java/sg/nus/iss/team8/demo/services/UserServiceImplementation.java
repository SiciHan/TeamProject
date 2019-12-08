
  package sg.nus.iss.team8.demo.services;
  
 import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import
  org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.models.User;
import sg.nus.iss.team8.demo.repositories.UserRepository;
  
 
  @Service 

  public class UserServiceImplementation implements UserService{
  
 @Autowired
 private UserRepository ur;
  
  @Autowired
  public void setUserRepository(UserRepository ur)
  { this.ur=ur; }

    @Override
	@Transactional
	public User findUser(String name) {
		return ur.findByUsername(name);
	}

	@Override
	public String findUserType(String name) {
		return ur.findUserType(name);
	}

	
  
 
    
  

  

  
  }
