package sg.nus.iss.team8.demo.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sg.nus.iss.team8.demo.models.MailModel;
import sg.nus.iss.team8.demo.repositories.EmailRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class MailServiceImplementation implements MailService {
	@Resource
	private EmailRepository erepo;
	
	@Autowired
	public void setEmailRepository(EmailRepository erepo) {
		this.erepo = erepo;
	}

    private JavaMailSender mailSender;
    private MimeMessage mimeMessage;
    //private static Logger logger = Logger.getLogger(MailServiceImpl.class);
    
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 发送html格式的，带附件的邮件
     */
    @Override
    public void sendAttachMail(MailModel mail) {

        try {
            MimeMessageHelper mailMessage = new MimeMessageHelper(
                    this.mimeMessage, true, "UTF-8");
            mailMessage.setFrom(mail.getFromAddress());// 设置邮件消息的发送者

            mailMessage.setSubject(mail.getSubject());// 设置邮件消息的主题
            mailMessage.setSentDate(new Date());// 设置邮件消息发送的时间
            mailMessage.setText(mail.getContent(), true); // 设置邮件正文，true表示以html的格式发送

            String[] toAddresses = mail.getToAddresses().split(";");// 得到要发送的地址数组
            for (int i = 0; i < toAddresses.length; i++) {
                mailMessage.setTo(toAddresses[i]);
                for (String fileName : mail.getAttachFileNames()) {
                    mailMessage.addAttachment(fileName, new File(fileName));
                }
                // 发送邮件
                this.mailSender.send(this.mimeMessage);
                //logger.info("send mail ok=" + toAddresses[i]);
            }
             

        } catch (Exception e) {
            //logger.error(e);
            e.printStackTrace();
        }

    }
    
    @Override
    public ArrayList<String> findAllEmail(String coursename){
    	return erepo.findAllEmailByCoursename(coursename);
    }
}
