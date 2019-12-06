package sg.nus.iss.team8.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import sg.nus.iss.team8.demo.models.*;
import sg.nus.iss.team8.demo.services.UserService;
import sg.nus.iss.team8.demo.services.UserServiceImplementation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;



@Controller
public class HomeController {
	
   
	 
	 
	  private UserService us;
	  
	  @Autowired 
	  public void setUserService(UserServiceImplementation usi) {
	  this.us=usi; }
	  
	  @InitBinder
	  protected void initBinder(WebDataBinder binder) {
		//binder.addValidators(new ProductValidator());
	  }
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		model.addAttribute("user", new UserSession());
		return "login";
	}
	
	@GetMapping("/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "home";
	}
	
	@PostMapping("/authenticate")
	public String getAuthentication(@ModelAttribute("user") UserSession user, BindingResult bindingResult) {
		String view =""; 
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
	
		if ( bindingResult.hasErrors())
		{
			view = "login";
		}
		else 
		{
			String input = user.getName();
			String password = user.getPassword();
			sg.nus.iss.team8.demo.models.User usr = us.findUser(user.getName());
			System.out.println(password);
			
			if (input.equals(usr.getUsername()) )
			{	
				if (passwordEncoder.matches(password, usr.getPasswordHash()))
				  { view= "redirect:/administrator/facultymanagement"; }
			}
			/*// reserve for student 
			 * if (input.equals(usr.getUsername()) ) { if (passwordEncoder.matches(password,
			 * usr.getPasswordHash())) { if(usr.getUserType() == "Student") view=
			 * "redirect:/student/applycourse"; } }
			 */
			
			/*// reserve for faculty 
			 * if (input.equals(usr.getUsername()) ) { if (passwordEncoder.matches(password,
			 * usr.getPasswordHash())) { if(usr.getUserType() == "Faculty") view=
			 * "redirect:/faculty/faculty_home"; } }
			 */
			else
			{
				view = "login";
			}
		}
		return view;
	}
	
	
	
	
}

	

