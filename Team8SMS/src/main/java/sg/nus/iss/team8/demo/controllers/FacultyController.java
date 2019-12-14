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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
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

	private StatusService staservice;

	private LeaveService lservice;
	private UserService userservice;

	public GenerateReportService getGrs() {
		return grs;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	@Autowired
	public void setGrs(GenerateReportService grs) {
		this.grs = grs;
	}

	@Autowired
	public void setFacultyService(FacultyService fservice) {
		this.fservice = fservice;
	}

	@Autowired
	public void setUserService(UserService userservice) {
		this.userservice = userservice;
	}

	@Autowired
	public void setStudentService(StudentService sservice) {
		this.sservice = sservice;
	}

	@Autowired
	public void setStatusService(StatusService staservice) {
		this.staservice = staservice;
	}

	@Autowired
	public void setLeaveService(LeaveService lservice) {
		this.lservice = lservice;
	}

	@GetMapping("/home")
	public String getHomePage(Model model, HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);

		return "faculty_home";
	}
	

	@GetMapping("/mycourses")
	public String getCourses(Model model, HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);
		return "faculty_courses";
	}

	@GetMapping("/this_course")
	public String getThisCourse(Model model, @RequestParam("coursename") String coursename,
			HttpServletRequest request) {
		ArrayList<Student> students = sservice.findStudentsByCourseName(coursename);
		model.addAttribute("students", students);
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);
		model.addAttribute("coursename", coursename);
		return "course_class_list";

	}

	@GetMapping("/grade")
	public String getGrade(Model model,
			@RequestParam(value = "coursename", required = false, defaultValue = "-") String coursename,
			HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);
		ArrayList<Courserun> courseruns = fservice.findAllCourserunsByFacultyId(faculty.getFacultyId());
		model.addAttribute("courseruns", courseruns);
		String courseCode = "-";
		Semester semester = new Semester();
		for (Courserun c : courseruns) {
			if (coursename.equals(c.getCourseName())) {
				courseCode = c.getCourseCode();
				semester = c.getSemester();
				break;
			}
		}
		model.addAttribute("courseKey", courseCode + (semester == null ? "" : semester.getSemester()));
		List<CourserunStudent> courserunstudents = new ArrayList<>();
		if (coursename != null && !coursename.equals("-")) {
			courserunstudents = fservice.findAllStudents(coursename);
		}
		model.addAttribute("courserunstudents", courserunstudents);
		CourserunStudentListWrapper wrapper = new CourserunStudentListWrapper();
		wrapper.setCourserunStudents(courserunstudents);
		model.addAttribute("wrapper", wrapper);
		return "faculty_grade";
	}

	@GetMapping("/report")
	public String getReport(Model model,
			@RequestParam(value = "coursename", required = false, defaultValue = "-") String coursename,
			@RequestParam(value = "grade", required = false, defaultValue = "-") String grade,
			HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);
		ArrayList<Courserun> courseruns = fservice.findAllCourserunsByFacultyId(faculty.getFacultyId());
		model.addAttribute("courseruns", courseruns);
		String courseCode = "-";
		Semester semester = new Semester();
		for (Courserun c : courseruns) {
			if (coursename.equals(c.getCourseName())) {
				courseCode = c.getCourseCode();
				semester = c.getSemester();
				break;
			}
		}
		model.addAttribute("courseKey", courseCode + (semester == null ? "" : semester.getSemester()));
		List<CourserunStudent> courserunstudents = new ArrayList<>();
		courserunstudents = fservice.findAllCourserunStudentList(coursename, grade);
		int[] gradeDataArray = new int[10];
		Double avg = new Double(0.0);
		avg += 0.0;
		if (coursename != null && !coursename.equals("-")) {

			for (CourserunStudent cs : courserunstudents) {
				switch (cs.getGrade()) {
				case "A+":
					gradeDataArray[0]++;
					avg += 5.0;
					break;
				case "A":
					gradeDataArray[1]++;
					avg += 5.0;
					break;
				case "A-":
					gradeDataArray[2]++;
					avg += 4.5;
					break;
				case "B+":
					gradeDataArray[3]++;
					avg += 4.0;
					break;
				case "B":
					gradeDataArray[4]++;
					avg += 3.5;
					break;
				case "B-":
					gradeDataArray[5]++;
					avg += 3.0;
					break;
				case "C+":
					gradeDataArray[6]++;
					avg += 2.5;
					break;
				case "C":
					gradeDataArray[7]++;
					avg += 2.0;
					break;
				case "D+":
					gradeDataArray[8]++;
					avg += 1.5;
					break;
				case "D":
					gradeDataArray[9]++;
					avg += 1.0;
					break;
				}
			}
		} else {
			coursename = "-";
		}
		if (grade == null || grade.equals("-")) {
			grade = "-";
		}

		model.addAttribute("coursename", coursename);
		model.addAttribute("grade", grade);
		model.addAttribute("gradeDataArray", gradeDataArray);
		model.addAttribute("courserunstudents", courserunstudents);
		try {
			avg = avg/courserunstudents.size();
		}
		catch(Exception e) {
			avg = 0.0;
			double a = avg.doubleValue();
			String res_avg = String.format("%.2f", a);
			model.addAttribute("avg", res_avg);
			return "faculty_report";
		}
		//avg = avg/courserunstudents.size();
		double a = avg.doubleValue();
		String res_avg = String.format("%.2f", a);
		model.addAttribute("avg", res_avg);
		return "faculty_report";
	}

	@PostMapping("/grade/submit")
	public String submitStudentGrades(@ModelAttribute("wrapper") CourserunStudentListWrapper wrapper, Model model) {
		List<CourserunStudent> courserunstudents = wrapper.getCourserunStudents();
		// fservice.saveCourserunStudents(courserunStudents);
		Status status = staservice.findByStatusId(9);
		for (CourserunStudent crs : courserunstudents) {
			if (crs.getGrade() != "N") {
				crs.setStatus(status);
			}
			sservice.saveCourserunStudent(crs);
		}
		return "redirect:/faculty/grade";
	}

	@GetMapping("/movement")
	public String movementRegister(Model model, HttpServletRequest request,
			@RequestParam(required = false, name = "yearmonth") String ymstring) {
		YearMonth ym = YearMonth.now();
		if (ymstring != null && !ymstring.isEmpty()) {

			ym = YearMonth.of(Integer.parseInt(ymstring.substring(0, 4)), Integer.parseInt(ymstring.substring(5)));
		}

		ArrayList<Leave> leaves = lservice.findLeavesByYearMonth(ym);
		ArrayList<YearMonth> yearMonths = lservice.findAllYearMonths(ym);
		ArrayList<String> username = lservice.findAllUserName(leaves);
		HashMap<String, Leave> usernameLeaves = lservice.MergeListToMap(leaves, username);
		// by default we will display the currentMonth leave
		model.addAttribute("leaves", usernameLeaves);
		model.addAttribute("yearMonths", yearMonths);
		model.addAttribute("selectedmonth", ym);
		UserSession user = (UserSession) request.getSession(false).getAttribute("user");		
		Faculty faculty = fservice.findFacultyByUserName(user.getName());
		model.addAttribute("faculty", faculty);
		return "faculty_movementregister";
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
	public String createLeave(@Valid @ModelAttribute("leave") Leave leave, BindingResult bindingResult, Model model) {
		// leave.setStatus(status);
		if (bindingResult.hasErrors()) {
			model.addAttribute("leave", leave);
			Faculty faculty = fservice.findFacultyById(102);
			model.addAttribute("faculty", faculty);
			return "faculty_leave";
		} else if (leave.getId().getStartDate().after(leave.getEndDate())) {
			return "end_startDateError";
		}
		Status status = staservice.findByStatusId(4);
		model.addAttribute("status", status);
		leave.setStatus(status);
		lservice.saveLeave(leave);

		return "LeaveApplicationSubmit";
	}

	@RequestMapping("/downloadCSV/classlist")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("coursename") String coursename) throws IOException {
		// create a list of object

		List<Student> students = sservice.findStudentsByCourseName(coursename);
//		for(Student s:students) {
//			System.out.println(s);
//		}

		String[] headers = new String[] { "Student ID", "Name", "Gender", "Birth Date", "Degree", "Address", "Mobile",
				"Email", "Semester", "Status" };
		grs.ExportCSV(request, response, students, headers);
	}

	@RequestMapping("/exportGrades")
	public void downloadGrades(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("coursename") String coursename) throws IOException {
		// create a list of object
		List<CourserunStudent> crstudents = fservice.findAllStudents(coursename);
		// List<Student> students = sservice.findStudentsByCourseName(coursename);
//		for(Student s:students) {
//			System.out.println(s);
//		}

		String[] headers = new String[] { "Course Name", "Student ID", "Name", "Degree", "Mobile", "Email", "Grade",
				"Status" };
		grs.ExportCSV(request, response, crstudents, headers);
	}
}
