package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService {
	@Resource
	private StudentRepository studentRepository;

	public StudentRepository getSr() {
		return studentRepository;
	}
	@Autowired

	public void setStudentRepository(StudentRepository sr) {
		this.studentRepository = sr;
	}
	
	@Override
	@Transactional
	public Student findStudent(int id) {
		return studentRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public ArrayList<Student> findAllStudents(){
		ArrayList<Student> sl = (ArrayList<Student>) studentRepository.findAll();
		return sl;
	}
	
	@Override
	@Transactional
	public Student createStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}
	
	@Override
	@Transactional
	public void removeStudent(Student student) {
		studentRepository.delete(student);
	}

}
