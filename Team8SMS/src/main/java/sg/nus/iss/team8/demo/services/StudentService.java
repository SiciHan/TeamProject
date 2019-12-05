package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import sg.nus.iss.team8.demo.models.Student;

public interface StudentService {
	
	ArrayList<Student> findAllStudents();
	
	Student findStudent(int id);
	
	Student createStudent(Student student);
	
	Student updateStudent(Student student);
	
	void removeStudent(Student student);
}
