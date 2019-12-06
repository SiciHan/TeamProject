package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Student;

@Service
public interface StudentService {
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid);
	
	ArrayList<Student> findAllStudents();
	
	Student findStudent(int id);
	
	Student createStudent(Student student);
	
	Student updateStudent(Student student);
	
	void removeStudent(Student student);
	
	ArrayList<Student> findStudentsByCourseName(String courseName);
}
