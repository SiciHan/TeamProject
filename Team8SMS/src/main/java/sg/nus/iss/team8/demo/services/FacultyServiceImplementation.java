package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.logging.Filter;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.repositories.*;

@Service
public class FacultyServiceImplementation implements FacultyService {
	private FacultyRepository facultyRepository;
	
	@Autowired
	public void setFacultyRepository(FacultyRepository facultyRepository) {
		this.facultyRepository = facultyRepository; 
	}
	
	
}
