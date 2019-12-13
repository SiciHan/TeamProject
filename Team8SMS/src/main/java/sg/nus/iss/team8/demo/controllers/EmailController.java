package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.nus.iss.team8.demo.services.*;
import sg.nus.iss.team8.demo.models.*;

@Controller
@RequestMapping("/email")
public class EmailController {
	private MailService eService;

	@Autowired
	public void setMailService(MailService eService) {
		this.eService = eService;
	}
	
//	@RequestMapping(value = "/send")
//    public String sendEmail(HttpServletRequest request, @ModelAttribute("anno") Announcement anno, Model model) {
//        anno = (Announcement) model.getAttribute("anno");
//		String  mail_title  = "New Announcement !";  
//        String  mail_content = anno.getMessage();  
//        MailModel email = MailModel.getInstance();     
//        email.sentEmails("huangyuzhe2019@163.com",mail_title,mail_content); 
//        //ArrayList<String> emails = eService.findAllEmail(coursename);
//        return "release_successful";
//    }
	
	@RequestMapping(value = "/prompt")
    public String promptEmail(@RequestParam("coursename") String coursename, Model model) {
		Announcement anno = new Announcement();
		model.addAttribute("anno", anno);
		model.addAttribute("coursename", coursename);
		return "announcement_prompt";
	}
	
	@PostMapping("/save")
	public String saveEmail(@Valid @ModelAttribute("anno") Announcement anno, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { 
			model.addAttribute("anno", anno);
			return "announcement_prompt"; 
		}
		eService.saveAnno(anno);
		ArrayList<String> addresses = eService.findAllEmail(anno.getId().getCourserunname());
//		addresses.add("E0457801@u.nus.edu");
//		addresses.add("E0457834@u.nus.edu");
//		addresses.add("E0003064@u.nus.edu");
		String  mail_title  = "New Announcement !";  
        String  mail_content = anno.getMessage();
        mail_content += "\nPlease login to check:\n'http://localhost:8080/login/student'";
        MailModel email = MailModel.getInstance(); 
        for(String add : addresses) {
        	email.sentEmails(add,mail_title,mail_content); 
        }
        return "release_successful";
	}
}
