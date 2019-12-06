package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Student;

@Service
public interface StudentService {
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid);
	public ArrayList<Courserun> findAvailableCourserun(int studentid);

	public ArrayList<CourserunStudent> findCancelledCourserunStudents(int id);

	public boolean courserunStudentExist(int id, String courseCode, int semesterid);

	public void setStatusToPending(int id, String courseCode, int semesterid);

	public void createCourserunStudent(int id, String courseCode, int semesterid);

	public void setStatusToCancelled(int id, String courseCode, int semesterid);
	
	ArrayList<Student> findAllStudents();
	
	Student findStudent(int id);
	
	Student createStudent(Student student);
	
	Student updateStudent(Student student);
	
	void removeStudent(Student student);
	
	ArrayList<Student> findStudentsByCourseName(String courseName);

}
