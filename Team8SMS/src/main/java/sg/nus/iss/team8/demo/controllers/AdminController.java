package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import sg.nus.iss.team8.demo.services.FacultyService;
import sg.nus.iss.team8.demo.services.FacultyServiceImplementation;

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
}
