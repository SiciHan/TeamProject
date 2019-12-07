package sg.nus.iss.team8.demo.controllers;


import javax.validation.Valid;

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
	
	@GetMapping("/")
	public String getHome() {
		return "index";
	}
	
	
	@GetMapping("/login/student")
	public String getStudentLoginPage(Model model) {
		model.addAttribute("user", new UserSession());
		return "studentLogin";
	}
	
	@GetMapping("/login/staff")
	public String getStaffLoginPage(Model model) {
		model.addAttribute("user", new UserSession());
		return "staffLogin";
	}
	
	@GetMapping("/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "home";
	}
	
	@PostMapping("/auth")
	public String getStaffAuthentication(@Valid @ModelAttribute("user") UserSession user, BindingResult binder) {
		String view =""; 
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
	
		if ( binder.hasErrors())
		{
			view = "staffLogin";
		}
		else 
		{
			String input = user.getName();
			String password = user.getPassword();
			sg.nus.iss.team8.demo.models.User usr = us.findUser(user.getName());
			System.out.println(password);
			
			if (input.equals("issl") )
			{	
				if (passwordEncoder.matches(password, usr.getPasswordHash()))
				  { view = "forward:/administrator/facultymanagement"; }
				
			}
			
			else if (input.equals(usr.getUsername()) )
			{	
				if (passwordEncoder.matches(password, usr.getPasswordHash()))
				  { view = "forward:/administrator/addfaculty"; } // for faculty home 
				
			}
			else
			{
				view = "staffLogin";
			}
		}
		return view;
	}
	
	
	@PostMapping("/authe")
	public String getStudentAuthentication(@ModelAttribute("user") UserSession user, BindingResult bindingResult,Model model) {
		String view =""; 
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
	
		if ( bindingResult.hasErrors())
		{
			model.addAttribute("user", user);
			view = "studentLogin";
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
				  { view= "forward:/student/applycourse"; }
			}
//			if (input.equals(usr.getUsername())) {
//				if (passwordEncoder.matches(password, usr.getPasswordHash())) {
//					if (usr.getUserType() == "Student")
//						view = "redirect:/student/applycourse";
//				}
//			}
			
			
			/*   reserve for faculty 
			 * if (input.equals(usr.getUsername()) ) { if (passwordEncoder.matches(password,
			 * usr.getPasswordHash())) { if(usr.getUserType() == "Faculty") view=
			 * "redirect:/faculty/faculty_home"; } }
			 */
			else
			{
				view = "studentLogin";
			}
		}
		return view;
	}
	
	
	
	
}

	

