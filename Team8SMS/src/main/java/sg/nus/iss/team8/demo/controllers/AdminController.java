package sg.nus.iss.team8.demo.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Department;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.Leave_PK;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.models.Status;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.services.AdminService;
import sg.nus.iss.team8.demo.services.AdminServiceImpl;
import sg.nus.iss.team8.demo.services.FacultyService;
import sg.nus.iss.team8.demo.services.FacultyServiceImplementation;
import sg.nus.iss.team8.demo.services.GenerateReportService;
import sg.nus.iss.team8.demo.services.GenerateReportServiceImpl;


@Controller
public class AdminController {

	private AdminService aService;
	private GenerateReportService grs;
	
	// injection of faculty service
	@Autowired
	public void setAdminService(AdminServiceImpl aService) {
		this.aService = aService;
	}
	
	@Autowired
	public void setGrs(GenerateReportServiceImpl grs) {
		this.grs = grs;
	}

	@InitBinder
	private void initUserBinder(WebDataBinder binder) {
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
//		binder.addValidators(studentValidator);
		// binder.addValidators(new ProductValidator());
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@GetMapping("/facultymanagement")
	public String getFacultyManagement(Model model) {
		ArrayList<Faculty> flist = new ArrayList<Faculty>();
		flist.addAll(aService.findAllFaculty());
		model.addAttribute("faculty", flist);
		return "facultymanagement";
	}

	@GetMapping("/addfaculty")
	public String addFaculty(Model model) {
		ArrayList<Status> slist = new ArrayList<Status>();
		ArrayList<Department> dlist = new ArrayList<Department>();
		Integer newFacultyId = aService.newFacultyId();
		slist.addAll(aService.findAllStatuses());
		dlist.addAll(aService.findAllDepartments());
		model.addAttribute("statuses", slist);
		model.addAttribute("departments", dlist);
		model.addAttribute("newFacultyId", newFacultyId);
		Faculty f = new Faculty();
		model.addAttribute("faculty", f);
		return "facultyform";
	}

	@PostMapping("/savefaculty")
	public String saveFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "facultyform";
		aService.saveFaculty(faculty);
		return "redirect:/facultymanagement";
	}

	@GetMapping("/editfaculty/{id}")
	public String editFaculty(Model model, @PathVariable("id") Integer id) {
		Faculty f = aService.findFacultyById(id);
		model.addAttribute("faculty", f);
		ArrayList<Status> slist = new ArrayList<Status>();
		ArrayList<Department> dlist = new ArrayList<Department>();
		slist.addAll(aService.findAllStatuses());
		dlist.addAll(aService.findAllDepartments());
		model.addAttribute("statuses", slist);
		model.addAttribute("departments", dlist);
		return "facultyform";
	}

	@GetMapping("/deletefaculty/{id}")
	public String deleteFaculty(@PathVariable("id") Integer id) {
		Faculty f = aService.findFacultyById(id);
		aService.deleteFaculty(f);
		return "redirect:/facultymanagement";
	}
	
	@GetMapping("/administrator")
	public String getAdmin(Model model) {
		
		ArrayList<Semester> allSemesters = aService.findAllSemsters();
		Semester currentSemester = aService.currentSemester();
		allSemesters.add(currentSemester);
		
		model.addAttribute("allSemesters", allSemesters);
		model.addAttribute("currentSemester", currentSemester);
		return "administrator";
	}
	
	@PostMapping("/savecurrentsemester")
	public String saveCurrentSemester(@ModelAttribute Semester sem, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "administrator";
		}
		aService.saveSemester(sem);
		int graduationThreshold = 20;
		aService.applyGraduatedStatus(sem, graduationThreshold);
		return "redirect:/administrator";
	}

	@GetMapping("/studentmanagement")
	public String listAllStudents(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Page<Student> studentPage = aService.pageAllStudents(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("studentPage", studentPage);
		
		int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        return "studentmanagement";
	}

	@GetMapping("/addstudent")
	public String showAddStudentForm(Model model) {
		Student student = new Student();
		Integer newStudentId = aService.newStudentId();
		Semester currentSemester = aService.currentSemester();
		model.addAttribute("student", student);
		model.addAttribute("newStudentId", newStudentId);
		model.addAttribute("currentSemester", currentSemester);
		return "studentform";
	}

	@PostMapping("/savestudent")
	public String saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "studentform";
		}

		aService.saveStudent(student);
		return "redirect:/studentmanagement";
	}

	@GetMapping("/editstudent/{id}")
	public String showStudentEditForm(Model model, @PathVariable("id") Integer id) {
		Student student = aService.findStudent(id);
		model.addAttribute("student", student);
		return "studentform";
	}

	@GetMapping("/deletestudent/{id}")
	public String deleteStudent(Model model, @PathVariable("id") Integer id) {
		Student student = aService.findStudent(id);
		aService.removeStudent(student);
		return "redirect:/studentmanagement";
	}
	@GetMapping("/courseapplication")
	public String listPendingCourseApplications(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Page<CourserunStudent> pendingPage = aService.pagePendingStudents(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("pendingPage", pendingPage);
		
		int totalPages = pendingPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pendingPageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pendingPageNumbers", pendingPageNumbers);
        }
        
        return "courseapplication";
	}
	
	@GetMapping("/{acceptOrReject}/{id}/{courseCode}/{semesterid}")
	public String acceptOrRejectCourseApplication(@PathVariable(name="acceptOrReject") String acceptOrReject,
			@PathVariable(name="id") int id, 
			@PathVariable(name="courseCode") String courseCode,
			@PathVariable(name="semesterid") int semesterid) {
		
		int status;
		if(acceptOrReject.equalsIgnoreCase("acceptcourse")) {
			status = 8; // 8 -> Undergoing
		} else {
			status = 7; // 7 -> Rejected
		}
		aService.setCourserunStudentStatus(id,courseCode,semesterid, status);
		return "redirect:/courseapplication";
	}
	
	
	
	@GetMapping("/courserunmanagement")
	public String listCourseruns(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);


		Page<Courserun> courserunPage = aService.pageCourserun(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("courserunPage", courserunPage);
		
		int totalPages = courserunPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> courserunPageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("courserunPageNumbers", courserunPageNumbers);
        }
        
        return "courserunmanagement";
	}
	
	private String concatSemester(String longSemLabel) {
		String s1 = longSemLabel.substring(0, 2);
		String s2 = longSemLabel.substring(4, 7);
		String s3 = longSemLabel.substring(9, 12);
		String s4 = longSemLabel.substring(14);

		return s1 + s2 + s3 + s4;
	}

	@PostMapping("/savecourserun")
	public String saveCourserun(@Valid @ModelAttribute Courserun course, @RequestParam("currentSemester")String semLabel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "courserunform";
		}
		if(semLabel != "") {
			aService.findOrAddSemester(semLabel);
			course.setSemester(aService.findOrAddSemester(semLabel));
			String shortSemLabel = concatSemester(semLabel);
			course = aService.concatCourseNameWithYear(course, shortSemLabel);
		}
		
		aService.saveCourserun(course);
		return "redirect:/courserunmanagement";
	}
	
	@GetMapping("/addcourserun")
	public String showAddCourseForm(Model model) {
		Courserun course = new Courserun();
		String semLabel=null;
		ArrayList<Faculty> flist = aService.findAllFaculty();
		model.addAttribute("flist", flist);
		model.addAttribute("course", course);
		model.addAttribute("currentSemester", semLabel);
		
		return "courserunform";
	}

	@GetMapping("/editcourserun/{courseCode}/{semesterid}")
	public String showCourserunEditForm(Model model, @PathVariable(name = "courseCode") String courseCode,
			@PathVariable(name = "semesterid") int semesterid) {
		Courserun course = aService.findCourserun(courseCode, semesterid);
		ArrayList<Faculty> flist = aService.findAllFaculty();
		model.addAttribute("flist", flist);
		model.addAttribute("course", course);
		return "courserunform";
	}

	@GetMapping("/deletecourserun/{courseCode}/{semesterid}")
	public String deleteCourserun(Model model, @PathVariable(name = "courseCode") String courseCode,
			@PathVariable(name = "semesterid") int semesterid) {
		Courserun course = aService.findCourserun(courseCode, semesterid);
		// if courserunstudents is not empty, redirect to /viewcourserun/"+courseCode+"/"+semesterid;
		ArrayList<CourserunStudent> studentsPerCourse = aService.findStudentsByCourseName(course.getCourseName());
		if(studentsPerCourse.isEmpty()) {
			aService.removeCourserun(course);
			return "redirect:/courserunmanagement";
		} else {
			return "redirect:/viewcourserun/"+courseCode+"/"+semesterid;
		}
		
	}

	@GetMapping("/viewcourserun/{courseCode}/{semesterid}")
	public String viewCourserun(Model model, @PathVariable(name = "courseCode") String courseCode,
			@PathVariable(name = "semesterid") int semesterid) {
		Courserun course = aService.findCourserun(courseCode, semesterid);
		ArrayList<CourserunStudent> studentsPerCourse = aService.findStudentsByCourseName(course.getCourseName());

		model.addAttribute("course", course);
		model.addAttribute("studentsPerCourse", studentsPerCourse);

		return "viewcourserun";
	}
	
	@GetMapping("/leaveapplication")
	public String leaveApplication(Model model) {
		ArrayList<Leave> llist = new ArrayList<Leave>();
		llist.addAll(aService.findAllLeave());
		model.addAttribute("leaves", llist);
		return "leaveapplication";
	}
	
	@GetMapping("/approveleave/{startDate}/{user}/{id}")
	public String approveLeaveApplication(@PathVariable("startDate") String startDate, 
			@PathVariable("user") String user, @PathVariable("id") int id) throws ParseException {
		int status = 6;
 		aService.approveLeave(startDate, user, id, status);
		return "redirect:/leaveapplication";
	}
	
	@GetMapping("/rejectleave/{startDate}/{user}/{id}")
	public String rejectLeaveApplication(@PathVariable("startDate") String startDate, 
			@PathVariable("user") String user, @PathVariable("id") int id) {
		int status = 7;
		aService.rejectLeave(startDate, user, id, status);
		return "redirect:/leaveapplication";
	}
	
	@GetMapping("/departmentmanagement")
	public String getDepartmentManagement(Model model) {
		ArrayList<Department> dlist = new ArrayList<Department>();
		dlist.addAll(aService.findAllDepartment());
		model.addAttribute("departments", dlist);
		return "departmentmanagement";
	}
	
	@GetMapping("/adddepartment")
	public String addDepartment(Model model) {
//		ArrayList<Department> departmentlist = new ArrayList<Department>();
		Integer newDapartmentId = aService.newDepartmentId();
//		model.addAttribute("departments", departmentlist);
		model.addAttribute("newDepartmentId", newDapartmentId);
		Department department = new Department();
		model.addAttribute("department", department);
		return "departmentform";
	}
	
	@PostMapping("/savedepartment")
	public String saveDepartment(@Valid @ModelAttribute Department department, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "departmentManagement";
		aService.saveDepartment(department);
		return "redirect:/departmentmanagement";
	}
	
	@GetMapping("/editdepartment/{id}")
	public String editDepartment(Model model, @PathVariable("id") Integer id) {
		Department d = aService.findDepartmentById(id);
		model.addAttribute("department", d);
		return "departmentform";
	}
	
	@GetMapping("/deletedepartment/{id}")
	public String deleteDepartment(@PathVariable("id") Integer id) {
		Department d = aService.findDepartmentById(id);
		aService.deleteDepartment(d);
		return "redirect:departmentmanagement";
	}
	
	@RequestMapping("/downloadCSV/classlist")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("coursename") String coursename) throws IOException {
		// create a list of object
		
			System.out.println("found " + coursename);
			ArrayList<CourserunStudent> students = aService.findStudentsByCourseName(coursename);
			System.out.println("found: " + students.size() + " students");
			System.out.println("trying to download");
			String[] headers=new String[]{"Course Name","Student Id", "Student Name", "Degree", "Mobile", "Email", "Grade", "Status"};
			System.out.println("request: " + request);
			System.out.println("response: " + response);
			
			grs.ExportCSV(request, response, students, headers);
	}
	
	@GetMapping("/viewstudentcourses/{studentid}")
	public String viewCourserun(Model model,
			@PathVariable(name = "studentid") int studentid) {
		Student student = aService.findStudent(studentid);
		ArrayList<CourserunStudent> coursesPerStudent = aService.findCoursesByStudentId(studentid);

		model.addAttribute("student", student);
		model.addAttribute("coursesPerStudent", coursesPerStudent);

		return "viewstudentcourses";
	}
}
