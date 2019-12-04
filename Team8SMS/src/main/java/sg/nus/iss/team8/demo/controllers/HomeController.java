package sg.nus.iss.team8.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import sg.nus.iss.team8.demo.services.UserService;
import sg.nus.iss.team8.demo.services.UserServiceImplementation;

@Controller
public class HomeController {
	
	private UserService us;
	@Autowired
	public void setUserService(UserServiceImplementation usi) {
		this.us=usi;
	}

}
