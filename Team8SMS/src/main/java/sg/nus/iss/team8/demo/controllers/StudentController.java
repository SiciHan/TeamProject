package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import java.time.LocalDateTime;
import java.time.Month;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Leave;

import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

import sg.nus.iss.team8.demo.models.User;
import sg.nus.iss.team8.demo.services.GenerateReportService;

import sg.nus.iss.team8.demo.services.LeaveService;
import sg.nus.iss.team8.demo.services.LeaveServiceImplementation;
import sg.nus.iss.team8.demo.services.StudentService;
import sg.nus.iss.team8.demo.services.StudentServiceImplementation;

@Controller
@RequestMapping("/student")
public class StudentController {
	private StudentRepository srepo;
	@Autowired
	public void setSrepo(StudentRepository srepo) {
		this.srepo = srepo;
	}
	public StudentRepository getSrepo() {
		return srepo;
	}
	
	
	private CourserunStudentRepository csrepo;
	@Autowired
	public void setCsrepo(CourserunStudentRepository csrepo) {
		this.csrepo = csrepo;
	}
	public CourserunStudentRepository getCsrepo() {
		return csrepo;
	}
	
	
	private StudentService ss;
	private LeaveService ls;
	private GenerateReportService grs;
	@Autowired 
	public void setStudentService(StudentServiceImplementation ssi) {
		this.ss=ssi;
	}
	@Autowired 
	public void setLeaveService(LeaveServiceImplementation lsi) {
		this.ls=lsi;
	}
	
	@Autowired
	public void setGrs(GenerateReportService grs) {
		this.grs = grs;
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
	
	@GetMapping("/movementregister")
	public String movementRegister(Model model, @RequestParam(required=false, name="yearmonth") String ymstring) {
		YearMonth ym=YearMonth.now();
		if(ymstring!=null && !ymstring.isEmpty()) {
			
			ym=YearMonth.of(Integer.parseInt(ymstring.substring(0,4)),Integer.parseInt(ymstring.substring(5)));
		}
		
		ArrayList<Leave> leaves=ls.findLeavesByYearMonth(ym);
		ArrayList<YearMonth> yearMonths=ls.findAllYearMonths(ym);
		ArrayList<String> username=ls.findAllUserName(leaves);
		HashMap<String,Leave> usernameLeaves=ls.MergeListToMap(leaves,username);
		//by default we will display the currentMonth leave
		model.addAttribute("leaves",usernameLeaves);
		model.addAttribute("yearMonths", yearMonths);
		model.addAttribute("selectedmonth",ym);
		return "movementregister";
	}

	@GetMapping("/mycourses")
	public String MyCourses(Model model){
		Student s=srepo.findNameById(10006);
		model.addAttribute("student", s);
		ArrayList<CourserunStudent>courserunstudentlist=new ArrayList<>();
		courserunstudentlist.addAll(csrepo.findCourseById(10006));
		model.addAttribute("courselist", courserunstudentlist);
		
		return "mycourses"; 
	}
	@GetMapping("/transcript")
	public String MyTranscript(Model model) {
		Student s=srepo.findNameById(10006);
		model.addAttribute("student", s);
		double points=ss.totalScorePoints(10006);
		int totalCredits=ss.totalCredits(10006);
		double cap=points/totalCredits;
		 cap=Math.round(cap*100.0)/100.0;
		 String gstatus=ss.graduationStatus(10006);
		 //Date today = Calendar.getInstance().getTime();
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();
		  String today=dtf.format(now);
		model.addAttribute("totalCredits", totalCredits);
		model.addAttribute("cap", cap);
		model.addAttribute("gstatus",gstatus);
		model.addAttribute("today", today);
		ArrayList<CourserunStudent>clist= csrepo.findCourseGradebyId(10006);
		model.addAttribute("clist", clist);
		
		return "mytranscript";
	}

}
