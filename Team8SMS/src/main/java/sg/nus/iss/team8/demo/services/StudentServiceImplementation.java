package sg.nus.iss.team8.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService {
	private StudentRepository studentRepository;

	public StudentRepository getSr() {
		return studentRepository;
	}
	@Autowired

	public void setStudentRepository(StudentRepository sr) {
		this.studentRepository = sr;
	}
	
	

}
