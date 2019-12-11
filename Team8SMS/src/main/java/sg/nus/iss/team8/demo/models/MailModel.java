package sg.nus.iss.team8.demo.models;

import java.util.Map;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
import org.springframework.mail.SimpleMailMessage;  
import org.springframework.mail.javamail.JavaMailSender;

public class MailModel {
	private static MailModel mailModel = null;  
    
    public ApplicationContext ctx = null;   
      
    public MailModel() {  
                 
        ctx = new ClassPathXmlApplicationContext("spring-mail.xml");     
    }
    
    public static MailModel getInstance()  
    {  
        if(mailModel==null)  
        {  
            synchronized (MailModel.class)   
            {  
                if(mailModel==null) {  
                	mailModel = new MailModel();  
                }  
            }  
        }  
        return mailModel;  
    }
    
    public void sentEmails(String emails,String subject,String text)  
    {             
           
        JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");     
           
        SimpleMailMessage mail = new SimpleMailMessage();   
        String email[] = emails.split(";");  
        for(int i=0;i<email.length;i++) {  
            try {     
                mail.setTo(email[i]);
                mail.setFrom("huangyuzhe2019@163.com");   
                mail.setSubject("New Announcement");
                mail.setText("You received a new announcement !");  
                sender.send(mail);     
            } catch (Exception e) {     
                e.printStackTrace();    
            }  
        }  
    } 
    
}