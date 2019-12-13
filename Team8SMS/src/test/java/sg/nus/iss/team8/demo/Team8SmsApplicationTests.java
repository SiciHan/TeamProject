package sg.nus.iss.team8.demo;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import sg.nus.iss.team8.demo.models.MailModel;

@SpringBootTest

class Team8SmsApplicationTests {

	

	@Test
	public void sendSimpleMail() throws Exception {
		String  mail_title  = "S";  
        String  mail_content    = "<a href='http://www.baidu.com'/>";  
        MailModel email = MailModel.getInstance();     
        email.sentEmails("845542640@qq.com",mail_title,mail_content); 
	}

}
