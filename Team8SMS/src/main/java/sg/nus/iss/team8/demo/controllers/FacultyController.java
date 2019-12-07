package sg.nus.iss.team8.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	
	@GetMapping("/home")
	public String getHome() {
		return "faculty_home";
	}
	
	@GetMapping("/courses")
	public String getCourses() {
		return "faculty_courses";
	}
	

	@GetMapping("/leave")
	public String getLeave() {
		return "faculty_leave";
	}

}
