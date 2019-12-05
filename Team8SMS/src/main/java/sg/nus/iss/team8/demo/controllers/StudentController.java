package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.services.StudentService;
import sg.nus.iss.team8.demo.services.StudentServiceImplementation;

@Controller
@RequestMapping("/student")
public class StudentController {

	private StudentService ss;
	@Autowired 
	public void setStudentService(StudentServiceImplementation ssi) {
		this.ss=ssi;
	}
	@GetMapping("/applycourse")
	public String applyCourse(int id, Model model) {
		
		  ArrayList<CourserunStudent> courses1=ss.findAvailableCourserunStudents(id);
		  ArrayList<CourserunStudent> courses2=ss.findPendingCourserunStudents(id);
		  ArrayList<CourserunStudent>
		  courses3=ss.findRejectedAndApprovedCourserunStudents(id);
		  
		  model.addAttribute("courses1",courses1);
		  model.addAttribute("courses2",courses2);
		  model.addAttribute("courses3",courses3);
		
		return "applycourse";
		
	}
	@GetMapping("/applycoursetest")
	public String applyCourseTest(){
		return "applycourse";
		
	}

	
}
