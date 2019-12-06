package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team8.demo.models.Courserun;
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
	public String applyCourse(Model model) {
		
		  Integer id=10013;//hardcoded. need to use userid
		  //display the courserun that
		  ArrayList<CourserunStudent> courses1=ss.findCancelledCourserunStudents(id);//all courserunstudent that is cancelled and ready for application.
		  ArrayList<Courserun> courses1a=ss.findAvailableCourserun(id);//all courserun that not applied yet
		  ArrayList<CourserunStudent> courses2=ss.findPendingCourserunStudents(id);
		  ArrayList<CourserunStudent> courses3=ss.findRejectedAndApprovedCourserunStudents(id);
		  
		  String m1="Not found";
		  model.addAttribute("courses1a",courses1a);
		  model.addAttribute("courses1",courses1);
		  model.addAttribute("courses2",courses2);
		  model.addAttribute("courses3",courses3);
		  model.addAttribute("message",m1);
		  model.addAttribute("studentId",id);
		  return "applycourse";
	}
	
	@GetMapping("/apply/{id}/{courseCode}/{semesterid}")
	public String applyCourseTest(@PathVariable(name="id") int id, 
								@PathVariable(name="courseCode") String courseCode,
								@PathVariable(name="semesterid") int semesterid)
	{
		if(ss.courserunStudentExist(id,courseCode,semesterid)) {
			ss.setStatusToPending(id,courseCode,semesterid);
		}
		else {
			ss.createCourserunStudent(id,courseCode,semesterid);
		}

		return "redirect:/student/applycourse";
		
	}
	
	@GetMapping("/cancelapplication/{id}/{courseCode}/{semesterid}")
	public String CancelCourseTest(@PathVariable(name="id") int id, 
								@PathVariable(name="courseCode") String courseCode,
								@PathVariable(name="semesterid") int semesterid)
	{
		if(ss.courserunStudentExist(id,courseCode,semesterid)) {
			ss.setStatusToCancelled(id,courseCode,semesterid);
		}
		else {
			ss.createCourserunStudent(id,courseCode,semesterid);
		}

		return "redirect:/student/applycourse";
		
	}

	
}
