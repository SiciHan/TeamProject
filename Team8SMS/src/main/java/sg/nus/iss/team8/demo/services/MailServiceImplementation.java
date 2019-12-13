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

import sg.nus.iss.team8.demo.models.Announcement;
import sg.nus.iss.team8.demo.models.MailModel;
import sg.nus.iss.team8.demo.repositories.AnnouncementRepository;
import sg.nus.iss.team8.demo.repositories.EmailRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class MailServiceImplementation implements MailService {
	@Resource
	private EmailRepository erepo;
	private AnnouncementRepository arepo;
	
	@Autowired
	public void setEmailRepository(EmailRepository erepo) {
		this.erepo = erepo;
	}
	
	@Autowired
	public void setAnnouncementRepository(AnnouncementRepository arepo) {
		this.arepo = arepo;
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

    
    @Override
    public ArrayList<String> findAllEmail(String coursename){
    	return erepo.findAllEmailByCoursename(coursename);
    }
    
    @Override
    public void saveAnno(Announcement anno) {
    	arepo.saveAndFlush(anno);
    }
}
