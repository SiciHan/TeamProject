package sg.nus.iss.team8.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sg.nus.iss.team8.demo.models.*;
import sg.nus.iss.team8.demo.services.*;
import java.io.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;

@Controller
@RequestMapping("/faculty")
//@SessionAttributes("user")
public class FacultyController {
	private FacultyService fservice;

	private StudentService sservice;

	private GenerateReportService grs;

	public GenerateReportService getGrs() {
		return grs;
	}

	@Autowired
	public void setGrs(GenerateReportService grs) {
		this.grs = grs;
	}

	@Autowired
	public void setFacultyService(FacultyServiceImplementation fservice) {
		this.fservice = fservice;
	}
	
	@Autowired
	public void setStudentService(StudentServiceImplementation sservice) {
		this.sservice = sservice;
	}
	
	@GetMapping("/home")
	public String getHomePage(Model model/* , @SessionAttribute("user") UserSession user */) {
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);

		return "faculty_home";
	}

	@GetMapping("/mycourses")
	public String getCourses(Model model/* , @SessionAttribute("faculty") Faculty faculty */) {
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);
		return "faculty_courses";
	}

	
	@GetMapping("/this_course")
	public String getThisCourse(Model model, @RequestParam("coursename") String coursename) {
		ArrayList<Student> students = sservice.findStudentsByCourseName(coursename);
		model.addAttribute("students", students);
		Faculty faculty = fservice.findFacultyById(102);
		model.addAttribute("faculty", faculty);
		model.addAttribute("coursename", coursename);
		return "course_class_list";

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
		//leave.setStatus(status);
		return "redirect:/faculty/home";
	}

	@RequestMapping("/downloadCSV/classlist")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("coursename") String coursename) throws IOException {
		// create a list of object
		List<Student> students = sservice.findStudentsByCourseName(coursename);
//		for(Student s:students) {
//			System.out.println(s);
//		}
		

		String[] headers=new String[]{"Student ID","Name", "Gender","Birth Date","Degree","Address","Address(2)","Mobile","Email","Semester","Status"};
		grs.ExportCSV(request, response, students, headers);
	}
}
