package sg.nus.iss.team8.demo.services;

import sg.nus.iss.team8.demo.models.*;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

	public ArrayList<String> findAllEmail(String coursename);
	
	public void saveAnno(Announcement anno);
}
