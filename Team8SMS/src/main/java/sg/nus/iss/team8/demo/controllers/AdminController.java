package sg.nus.iss.team8.demo.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Department;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.models.Status;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.models.UserSession;
import sg.nus.iss.team8.demo.services.AdminService;
import sg.nus.iss.team8.demo.services.AdminServiceImpl;
import sg.nus.iss.team8.demo.services.FacultyService;
import sg.nus.iss.team8.demo.services.GenerateReportService;
import sg.nus.iss.team8.demo.services.GenerateReportServiceImpl;

@Controller
public class AdminController {

	private AdminService aService;
	private GenerateReportService grs;
	private FacultyService fService;

	@Autowired
	public void setAdminService(AdminService aService) {
		this.aService = aService;
	}

	@Autowired
	public void setGrs(GenerateReportService grs) {
		this.grs = grs;
	}

	@Autowired
	public void setFacultyService(FacultyService fService) {
		this.fService = fService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new FacultyValidator());
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@GetMapping("/administrator")
	public String getAdmin(Model model, HttpServletRequest request, HttpSession session) {

//		try{
//			UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//			if(!user.getName().equals("issl")) throw new Exception("invalid user");
//		}catch(Exception e) {
//			return "redirect:/login/faculty";
//		}

		ArrayList<Semester> allSemesters = aService.findAllSemsters();
		Semester currentSemester = aService.currentSemester();
		int courseApplicationCounter = aService.countPendingCourses();
		int leaveApplicationCounter = aService.countPendingLeaves();
		ArrayList<Courserun> courseruns = aService.findAllCourserun();
		ArrayList<Courserun> toprintlist;
		if (session.getAttribute("toprintlist") == null) {
			toprintlist = new ArrayList<Courserun>();
		} else {
			toprintlist = (ArrayList<Courserun>) session.getAttribute("toprintlist");
		}
		model.addAttribute("courseApplicationCounter", courseApplicationCounter);
		model.addAttribute("leaveApplicationCounter", leaveApplicationCounter);
//		model.addAttribute("allSemesters", allSemesters);
		model.addAttribute("currentSemester", currentSemester);
		model.addAttribute("courseruns", courseruns);
		model.addAttribute("toprintlist", toprintlist);
		session.setAttribute("toprintlist", toprintlist);
		model.addAttribute("courserun", new Courserun());
		return "administrator";
	}

	@PostMapping("admin/generatereport/add")
	public RedirectView addCourseForPrinting(Courserun courserun, HttpSession session) {
		String course = courserun.getCourseName();
		Courserun cr = aService.findCourserunByName(course);
		ArrayList<Courserun> toprintlist = (ArrayList<Courserun>) session.getAttribute("toprintlist");
		System.out.println(toprintlist.size());
		System.out.println(toprintlist == null);
		toprintlist.add(cr);
		return new RedirectView("http://localhost:8080/administrator");
	}

	@GetMapping("admin/generatereport/remove")
	public RedirectView removeCourseForPrinting(@RequestParam("coursename") String coursename, HttpSession session) {
		Courserun cr = aService.findCourserunByName(coursename);
		ArrayList<Courserun> toprintlist = (ArrayList<Courserun>) session.getAttribute("toprintlist");
		toprintlist.remove(cr);
		return new RedirectView("http://localhost:8080/administrator");
	}

	@RequestMapping("admin/generatereport/export")
	public String exportCombinedCSV(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		// create a list of object
		if (session.getAttribute("toprintlist") == null) {
			return "redirect:/administrator";
		}
		ArrayList<Courserun> toprintlist = (ArrayList<Courserun>) session.getAttribute("toprintlist");
		grs.ExportCombinedCSV(request, response, toprintlist);
		session.removeAttribute("toprintlist");
		//return new RedirectView("http://localhost:8080/administrator");
		return null;
	}

	@GetMapping("/facultymanagement")
	public String getFacultyManagement(Model model, HttpServletRequest request) {
//		try{
//			UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//			if(!user.getName().equals("issl")) throw new Exception("invalid user");
//		}catch(Exception e) {
//			return "redirect:/login/faculty";
//		}

		ArrayList<Faculty> flist = new ArrayList<Faculty>();
		flist.addAll(aService.findAllFaculty());
		model.addAttribute("faculty", flist);
		return "facultymanagement";
	}

	@Transactional
	@GetMapping("/addfaculty")
	public String addFaculty(Model model, HttpServletRequest request) {
//		try{
//			UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//			if(!user.getName().equals("issl")) throw new Exception("invalid user");
//		}catch(Exception e) {
//			return "redirect:/login/faculty";
//		}
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

	// @Transactional
	@PostMapping("/savefaculty")
	public String saveFaculty(@Valid Faculty faculty, BindingResult bindingResult, HttpServletRequest request,
			Model model) {
//			try{
//				UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//				if(!user.getName().equals("issl")) throw new Exception("invalid user");
//			}catch(Exception e) {
//				return "redirect:/login/faculty";
//			}
		if (bindingResult.hasErrors()) {
			System.out.println("has error");
			ArrayList<Status> slist = new ArrayList<Status>();
			ArrayList<Department> dlist = new ArrayList<Department>();
			Integer newFacultyId = aService.newFacultyId();
			slist.addAll(aService.findAllStatuses());
			dlist.addAll(aService.findAllDepartments());
			model.addAttribute("statuses", slist);
			model.addAttribute("departments", dlist);
			model.addAttribute("newFacultyId", newFacultyId);
			model.addAttribute("faculty", faculty);
			return "facultyform";
		}
		System.out.println("no error");
		aService.saveFaculty(faculty);
		return "redirect:/facultymanagement";
	}

	@Transactional
	@GetMapping("/editfaculty/{id}")
	public String editFaculty(Model model, @PathVariable("id") Integer id, HttpServletRequest request) {
//			try{
//				UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//				if(!user.getName().equals("issl")) throw new Exception("invalid user");
//			}catch(Exception e) {
//				return "redirect:/login/faculty";
//			} 
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
	public String deleteFaculty(@PathVariable("id") Integer id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//			try{
//				UserSession user=(UserSession)request.getSession(false).getAttribute("user");
//				if(!user.getName().equals("issl")) throw new Exception("invalid user");
//			}catch(Exception e) {
//				return "redirect:/login/faculty";
//			}
		Faculty f = aService.findFacultyById(id);
		ArrayList<Courserun> coursesPerFaculty = fService.findAllCourserunsByFacultyId(id);
		if (coursesPerFaculty.isEmpty()) {
			aService.deleteFaculty(f);
		} else {
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "Failed");
			redirectAttributes.addFlashAttribute("existFaculty", f);
		}

		return "redirect:/facultymanagement";
	}

	@PostMapping("/savecurrentsemester")
	public String saveCurrentSemester(Semester sem, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		aService.saveSemester(sem);
		int graduationThreshold = 160;
		aService.applyGraduatedStatus(sem, graduationThreshold);

		redirectAttributes.addFlashAttribute("message", "Success");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/administrator";
	}

	@GetMapping("/studentmanagement")
	public String listAllStudents(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Page<Student> studentPage = aService.pageAllStudents(PageRequest.of(currentPage - 1, pageSize));
//			System.out.println("before binding studentPage");
		model.addAttribute("studentPage", studentPage);
//			System.out.println("after binding studentPage");

		int totalPages = studentPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
//	        System.out.println("before rendering view");
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
	public String deleteStudent(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		Student student = aService.findStudent(id);
		ArrayList<CourserunStudent> coursesPerStudent = aService.findCoursesByStudentId(id);
		if (coursesPerStudent.isEmpty()) {
			aService.removeStudent(student);
		} else {
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "Failed");
			redirectAttributes.addFlashAttribute("existStudent", student);
		}
		
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
			List<Integer> pendingPageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
					.collect(Collectors.toList());
			model.addAttribute("pendingPageNumbers", pendingPageNumbers);
		}

		return "courseapplication";
	}

	@GetMapping("/{acceptOrReject}/{id}/{courseCode}/{semesterid}")
	public String acceptOrRejectCourseApplication(@PathVariable(name = "acceptOrReject") String acceptOrReject,
			@PathVariable(name = "id") int id, @PathVariable(name = "courseCode") String courseCode,
			@PathVariable(name = "semesterid") int semesterid) {

		int status;
		if (acceptOrReject.equalsIgnoreCase("acceptcourse")) {
			status = 8; // 8 -> Undergoing
		} else {
			status = 7; // 7 -> Rejected
		}
		aService.setCourserunStudentStatus(id, courseCode, semesterid, status);
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
			List<Integer> courserunPageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
					.collect(Collectors.toList());
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

	@GetMapping("/addcourserun")
	public String showAddCourseForm(Model model) {
		Courserun course = new Courserun();
		String semLabel = null;
		ArrayList<Faculty> flist = aService.findAllFaculty();
		model.addAttribute("flist", flist);
		model.addAttribute("course", course);
		model.addAttribute("currentSemester", semLabel);

		return "courserunform";
	}

	@PostMapping("/savecourserun")
	public String saveCourserun(@Valid @ModelAttribute Courserun course, BindingResult bindingResult,
			@RequestParam("currentSemester") String semLabel) {
		if (bindingResult.hasErrors()) {
			return "courserunform";
		}
		if (semLabel != "") {
			aService.findOrAddSemester(semLabel);
			course.setSemester(aService.findOrAddSemester(semLabel));
			String shortSemLabel = concatSemester(semLabel);
			course = aService.concatCourseNameWithYear(course, shortSemLabel);
		}

		aService.saveCourserun(course);
		return "redirect:/courserunmanagement";
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
			@PathVariable(name = "semesterid") int semesterid, RedirectAttributes redirectAttributes) {

		Courserun course = aService.findCourserun(courseCode, semesterid);
		// if courserunstudents is not empty, redirect to
		// /viewcourserun/"+courseCode+"/"+semesterid;
		ArrayList<CourserunStudent> studentsPerCourse = aService.findStudentsByCourseName(course.getCourseName());
		if (studentsPerCourse.isEmpty()) {
			aService.removeCourserun(course);
		} else {
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "Failed");
			redirectAttributes.addFlashAttribute("failedCourse", course);
		}

		return "redirect:/courserunmanagement";
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
			@PathVariable("user") String user, @PathVariable("id") int id) {
		int status = 6;
		aService.approveLeave(startDate, user, id, status);
		return "redirect:/leaveapplication";
	}

	@GetMapping("/rejectleave/{startDate}/{user}/{id}")
	public String rejectLeaveApplication(@PathVariable("startDate") String startDate, @PathVariable("user") String user,
			@PathVariable("id") int id) {
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
//			ArrayList<Department> departmentlist = new ArrayList<Department>();
		Integer newDapartmentId = aService.newDepartmentId();
//			model.addAttribute("departments", departmentlist);
		model.addAttribute("newDepartmentId", newDapartmentId);
		Department department = new Department();
		model.addAttribute("department", department);
		return "departmentform";
	}

	@PostMapping("/savedepartment")
	public String saveDepartment(@Valid @ModelAttribute Department department, BindingResult bindingResult) {
		System.out.println(bindingResult.hasErrors());
		if (bindingResult.hasErrors())
			return "departmentform";
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

		int gotFaculty = aService.findFacultyInDepartment(id);
		System.out.println(gotFaculty);
		System.out.println(gotFaculty == 1);
		if (gotFaculty == 0) {
			Department d = aService.findDepartmentById(id);
			aService.deleteDepartment(d);
		} else {
			return "deletedepartmentalert";
		}

//			Department d = aService.findDepartmentById(id);
//			
//			try {
//				aService.deleteDepartment(d);
//			}catch (DataIntegrityViolationException e){
//				view = "deletedepartmentoncascade/{id}";
//			}
//				catch (Exception ex){
//				view = "deletedepartmentoncascade";
//			}
		return "redirect:/departmentmanagement";
	}

	@GetMapping("/admin_movementregister")
	public String admin_movementRegister(Model model,
			@RequestParam(required = false, name = "yearmonth") String ymstring) {
		YearMonth ym = YearMonth.now();
		if (ymstring != null && !ymstring.isEmpty()) {

			ym = YearMonth.of(Integer.parseInt(ymstring.substring(0, 4)), Integer.parseInt(ymstring.substring(5)));
		}

		ArrayList<Leave> leaves = aService.findLeavesByYearMonth(ym);
		ArrayList<YearMonth> yearMonths = aService.findAllYearMonths(ym);
		ArrayList<String> username = aService.findAllUserName(leaves);
		HashMap<String, Leave> usernameLeaves = aService.MergeListToMap(leaves, username);
		// by default we will display the currentMonth leave
		model.addAttribute("leaves", usernameLeaves);
		model.addAttribute("yearMonths", yearMonths);
		model.addAttribute("selectedmonth", ym);
		return "admin_movementregister";
	}

	@RequestMapping("/downloadCSV/classlist")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("coursename") String coursename) throws IOException {
		// create a list of object

		ArrayList<CourserunStudent> students = aService.findStudentsByCourseName(coursename);
		String[] headers = new String[] { "Course Name", "Student Id", "Student Name", "Degree", "Mobile", "Email",
				"Grade", "Status" };

		grs.ExportCSV(request, response, students, headers);
	}

	@GetMapping("/viewstudentcourses/{studentid}")
	public String viewStudentCourses(Model model, @PathVariable(name = "studentid") int studentid) {
		Student student = aService.findStudent(studentid);
		ArrayList<CourserunStudent> coursesPerStudent = aService.findCoursesByStudentId(studentid);

		model.addAttribute("student", student);
		model.addAttribute("coursesPerStudent", coursesPerStudent);

		return "viewstudentcourses";
	}

	@RequestMapping("/downloadCSV/gpalist")
	public void downloadCsvGpa(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// create a list of object
		double gpa;
		Map<Double, Student> map = new HashMap<>();

		List<Student> students = aService.findAllStudents();

		for (Student student : students) {
			gpa = aService.calculateGPA(student);
			map.put(gpa, student);
		}

		List<String> underGradsWithGpa = map.entrySet().stream()
				.map(entry -> entry.getValue().getStudentId() + "," + entry.getValue().getName() + ","
						+ entry.getValue().getGender() + "," + entry.getValue().getMobile() + ","
						+ entry.getValue().getEmail() + "," + entry.getValue().getSemester().getLabel() + ","
						+ entry.getValue().getStatus().getLabel() + "," + entry.getKey())
				.sorted().collect(Collectors.toList());

		String[] headers = new String[] { "Student_ID", "Student_Name", "Gender", "Mobile", "Email", "Semester",
				"Status", "GPA" };
		grs.ExportCSV(request, response, underGradsWithGpa, headers);
	}

}
