package sg.nus.iss.team8.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.services.AdminService;
import sg.nus.iss.team8.demo.services.AdminServiceImpl;
import sg.nus.iss.team8.demo.services.FacultyService;
import sg.nus.iss.team8.demo.services.FacultyServiceImplementation;
import sg.nus.iss.team8.demo.services.StudentService;

@Controller
@RequestMapping("/administrator")
public class AdminController {

	private AdminService aService;

	// injection of faculty service
	@Autowired
	public void setAdminService(AdminServiceImpl aService) {
		this.aService = aService;
	}

	@InitBinder
	private void initUserBinder(WebDataBinder binder) {
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
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
		ArrayList<Faculty> flist = new ArrayList<Faculty>();
		flist.addAll(aService.findAllFaculty());
		model.addAttribute("faculties", flist);
		Faculty f = new Faculty();
		model.addAttribute("faculty", f);
		return "facultyform";
	}

	@PostMapping("/savefaculty")
	public String saveFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "facultyform";
		aService.saveFaculty(faculty);
		return "redirect:/administrator/facultymanagement";
	}

	@GetMapping("/editfaculty/{id}")
	public String editFaculty(Model model, @PathVariable("id") Integer id) {
		Faculty f = aService.findFacultyById(id);
		model.addAttribute("faculty", f);
		return "facultyform";
	}

	@GetMapping("/deletefaculty/{id}")
	public String deleteFaculty(Model model, @PathVariable("id") Integer id) {
		Faculty f = aService.findFacultyById(id);
		aService.deleteFaculty(f);
		return "redirect:/administrator/facultymanagement";
	}

//	@GetMapping("/administrator")
//	public String getAdmin() {
//		return "administrator";
//	}

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
		model.addAttribute("student", student);
		return "studentform";
	}

	@PostMapping("/savestudent")
	public String saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "studentform";
		}

		aService.saveStudent(student);
		return "redirect:/administrator/studentmanagement";
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
		return "redirect:/administrator/studentmanagement";
	}

}
