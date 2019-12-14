package sg.nus.iss.team8.demo.controllers;

import javax.servlet.http.HttpSession;
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
		this.us = usi;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	@GetMapping("/")
	public String getHome() {
		return "index";
	}
	
	@GetMapping("/home")
	public String testHome() {
		return "home";
	}

	@GetMapping("/login/student")
	public String getStudentLoginPage(Model model, HttpSession session) {
		UserSession user=new UserSession();
		
		model.addAttribute("user",user );
		return "studentLogin";
	}

	@GetMapping("/login/faculty")
	public String getStaffLoginPage(Model model) {
		model.addAttribute("user", new UserSession());
		return "staffLogin";
	}

	@GetMapping("/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "logout";
	}

	
	  
	@PostMapping("/authenticate")
	public String getAuthentication(@Valid @ModelAttribute("user") UserSession user,BindingResult bindingResult, HttpSession session
			,Model model) {

		String view = "";
		String type = "";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {

			if (bindingResult.hasErrors()) {
				model.addAttribute("user", user);
				view = "staffLogin";
			} else

			{
				String input = user.getName();
				String password = user.getPassword();
				sg.nus.iss.team8.demo.models.User usr = us.findUser(user.getName());
				System.out.println(usr.toString());

				type = us.findUserType(user.getName());
				System.out.println(type);

				if (!us.findUserType(user.getName()).equals("Faculty")) {
					view = "errorLogin";
				}

				else {

					if (user.getName().equals("issl")) {
						if (passwordEncoder.matches(password, usr.getPasswordHash())) {
							//manually added sessions below
							session.setAttribute("user", user); 
							session.setMaxInactiveInterval(10*60);//10mins
							view = "redirect:/administrator";
						}

						else {
							view = "WrongPasswordStaff";
						}

					}

					else if (user.getName().equals(usr.getUsername())) {
						if (passwordEncoder.matches(password, usr.getPasswordHash())) {
							//manually added sessions below
							session.setAttribute("user", user); 
							session.setMaxInactiveInterval(10*60);//10mins
							view = "redirect:/faculty/home";
						} else {
							view = "WrongPasswordStaff";

						}
					}

				}

			}
		}

		catch (NullPointerException e) {
			view = "UserNotFound";
		}

		return view;
	}
	

   

	@PostMapping("/authe")
	public String getStudentAuthentication(@Valid @ModelAttribute("user") UserSession user, BindingResult bindingResult,
			Model model, HttpSession session) {
		String view = "";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		try {
			if (bindingResult.hasErrors()) {
				model.addAttribute("user", user);
				view = "studentLogin";
			}

			else {
				String input = user.getName();
				String password = user.getPassword();
				sg.nus.iss.team8.demo.models.User usr = us.findUser(user.getName());
				System.out.println(password);

				if (!us.findUserType(user.getName()).equals("Student")) {
					view = "errorLogin";
				} else {
					if (input.equals(usr.getUsername())) {
						if (passwordEncoder.matches(password, usr.getPasswordHash())) {
						
							//manually added sessions below
							  session.setAttribute("user", user); 
							  session.setMaxInactiveInterval(10*60);//10mins
							 							
							view = "redirect:/student/";
						} else {
							view = "WrongPassword";

						}
					}
				}
//			if (input.equals(usr.getUsername())) {
//				if (passwordEncoder.matches(password, usr.getPasswordHash())) {
//					if (usr.getUserType() == "Student")
//						view = "redirect:/student/applycourse";
//				}
//			}

				/*
				 * reserve for faculty if (input.equals(usr.getUsername()) ) { if
				 * (passwordEncoder.matches(password, usr.getPasswordHash())) {
				 * if(usr.getUserType() == "Faculty") view= "redirect:/faculty/faculty_home"; }
				 * }
				 */

			}
		} catch (NullPointerException e) {
			view = "UserNotFoundStudent";
		}
		return view;
	}

}
