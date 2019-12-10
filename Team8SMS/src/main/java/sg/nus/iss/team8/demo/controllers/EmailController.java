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
        MailModel mm = new MailModel();
        ArrayList<String> emails = eService.findAllEmail(coursename);
        //attach
        //String fileNames[] = { "G:/profession/Java/project/aidai/src/main/resources/conf/spring.xml" };
        //mm.setAttachFileNames(fileNames);
        mm.setFromAddress("845542640@qq.com");
        mm.setToAddresses("huangyuzhe2019@163.com;");
        mm.setContent("这是来自xx的一封信，如果你收到了，证明xxx的邮件功能搞定了！");
        mm.setSubject("xx测试（来自稀饭的一封信）");
        mailService.sendAttachMail(mm);
    }
}
