package sg.nus.iss.team8.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sg.nus.iss.team8.demo.models.*;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;

@Controller
@RequestMapping("/faculty")
@SessionAttributes("faculty")
public class FacultyController {
	@GetMapping("/home")
	public String getHomePage(Model model, @SessionAttribute("faculty") Faculty faculty) {
		model.addAttribute("faculty", faculty);
		return "faculty_home";
	}
	
	@GetMapping("/mycourses")
	public String getCourses(Model model, @SessionAttribute("faculty") Faculty faculty) {
		model.addAttribute("faculty", faculty);
		return "faculty_courses";
	}
	
	@GetMapping("/mycourses/this_course")
	public String getCourse(Model model, @RequestParam("coursename") String coursename) {
		
		return "faculty_specific_course";
	}
	
	@GetMapping("/mycourses/this_course/class_list")
	public String getClassList() {
		
		return "class_list";
	}
	
	@GetMapping("/score_card")
	public String getScore() {
		return "score_card";
	}
	
	@GetMapping("/movement")
	public String getMovement() {
		return "movement";
	}
	
	@GetMapping("/applyleave")
	public String getLeave() {
		return "faculty_apply_leave";
	}
	
}
