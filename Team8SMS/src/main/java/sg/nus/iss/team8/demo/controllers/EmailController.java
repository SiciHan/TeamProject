package sg.nus.iss.team8.demo.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.nus.iss.team8.demo.services.*;
import sg.nus.iss.team8.demo.models.*;

@Controller
@RequestMapping("/email")
public class EmailController {
	private MailService eService;

	@Autowired
	public void setMailService(MailServiceImplementation eService) {
		this.eService = eService;
	}
	
	@Autowired
    private MailService mailService;
	
	@RequestMapping(value = "/send")
    @ResponseBody
    public void sendEmail(HttpServletRequest request, @RequestParam("coursename") String coursename) {
        String  mail_title  = "S";  
        String  mail_content    = "<a href='http://www.baidu.com'/>";  
        MailModel email = MailModel.getInstance();     
        email.sentEmails("845542640@qq.com",mail_title,mail_content); 
        //ArrayList<String> emails = eService.findAllEmail(coursename);
        //attach
        //String fileNames[] = { "G:/profession/Java/project/aidai/src/main/resources/conf/spring.xml" };
        
    }
}
