package sg.nus.iss.team8.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.services.FacultyService;
import sg.nus.iss.team8.demo.services.FacultyServiceImplementation;
import sg.nus.iss.team8.demo.services.StudentService;

@Controller
@RequestMapping("/administrator")
public class AdminController {
	
	private FacultyService fservice;
	
	//injection of faculty service
	@Autowired
	public void setFacultyService(FacultyServiceImplementation fserviceimplementation) {
		this.fservice = fserviceimplementation;
	}
	@InitBinder
	private void initUserBinder(WebDataBinder binder) {
	}
	
	@GetMapping("/facultymanagement")
	public String getFacultyManagement(Model model) {
		ArrayList<Faculty> flist = new ArrayList<Faculty>();
		flist.addAll(fservice.findAllFaculty());
		model.addAttribute("faculty", flist);
		return"facultymanagement";
	}
	
	@GetMapping("/addfaculty")
	public String addFaculty(Model model) {
		ArrayList<Faculty> flist = new ArrayList<Faculty>();
		flist.addAll(fservice.findAllFaculty());
		model.addAttribute("faculties", flist);
		Faculty f = new Faculty();
		model.addAttribute("faculty", f);
		return "facultyform";
	}
	
	@PostMapping("/savefaculty")
	public String saveFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "facultyform";
		fservice.saveFaculty(faculty);
		return "redirect:/administrator/facultymanagement";
	}
	@GetMapping("/editfaculty/{id}")
	public String editFaculty(Model model, @PathVariable("id") Integer id) {
		Faculty f = fservice.findFacultyById(id);
		model.addAttribute("faculty", f);
		return "facultyform";
	}
	@GetMapping("/deletefaculty/{id}")
	public String deleteFaculty(Model model, @PathVariable("id") Integer id) {
		Faculty f = fservice.findFacultyById(id);
		fservice.deleteFaculty(f);
		return "redirect:/administrator/facultymanagement";
	}
	@Autowired
	private StudentService sService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/mm/yyyy"), true, 10)); 
	}

//	@GetMapping("/administrator")
//	public String getAdmin() {
//		return "administrator";
//	}

	@GetMapping("/studentmanagement")
	public String listAllStudents(Model model) {
		ArrayList<Student> slist = new ArrayList<Student>();
		slist.addAll(sService.findAllStudents());
		model.addAttribute("students", slist);
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

		sService.createStudent(student);
		return "redirect:/administrator/studentmanagement";
	}

	@GetMapping("/editstudent/{id}")
	public String showStudentEditForm(Model model, @PathVariable("id") Integer id) {
		Student student = sService.findStudent(id);
		model.addAttribute("student", student);
		return "studentform";
	}

	@GetMapping("/deletestudent/{id}")
	public String deleteStudent(Model model, @PathVariable("id") Integer id) {
		Student student = sService.findStudent(id);
		sService.removeStudent(student);
		return "redirect:/administrator/studentmanagement";
	}

}
