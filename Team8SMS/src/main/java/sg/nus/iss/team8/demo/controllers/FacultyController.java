package sg.nus.iss.team8.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sg.nus.iss.team8.demo.models.*;
import sg.nus.iss.team8.demo.services.*;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;

@Controller
@RequestMapping("/faculty")
//@SessionAttributes("user")
public class FacultyController {
	private FacultyService fservice;
	
	@Autowired
	public void setFacultyService(FacultyServiceImplementation fservice) {
		this.fservice = fservice;
	}
	
	@GetMapping("/home")
	public String getHomePage(Model model/*, @SessionAttribute("user") UserSession user*/) {
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);
		
		return "faculty_home";
	}
	
	@GetMapping("/mycourses")
	public String getCourses(Model model/*, @SessionAttribute("faculty") Faculty faculty*/) {
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);
		return "faculty_courses";
	}
	
	@GetMapping("/mycourses/this_course/{coursename}")
	public String getCourse(Model model, @PathVariable("coursename") String coursename) {
		
		return "faculty_specific_course";
	}
	
	@GetMapping("/mycourses/this_course/class_list")
	public String getClassList() {
		
		return "class_list";
	}
	
	@GetMapping("/grade")
	public String getGrade(Model model) {		
		ArrayList<Courserun> courseruns = fservice.findAllCourseruns();
		model.addAttribute("courseruns", courseruns);
		return "faculty_grade";
		
	}
	
	@GetMapping("/movement")
	public String getMovement() {
		return "movement";
	}
	
	@GetMapping("/applyleave")
	public String getLeave(Model model) {
		Leave leave = new Leave();
		model.addAttribute("leave", leave);
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);
		return "faculty_leave";
	}
	
	@PostMapping("/createleave")
	public String createLeave(@ModelAttribute("leave") Leave leave) {
		
		return "redirect:/faculty/home";
	}
	
}
