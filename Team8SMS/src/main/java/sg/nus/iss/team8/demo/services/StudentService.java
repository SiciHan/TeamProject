package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import sg.nus.iss.team8.demo.models.Announcement;
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
	
	public void saveCourserunStudent(CourserunStudent crs);
	
	ArrayList<Student> findAllStudents();

	Page<Student> pageAllStudents(Pageable pageable);
	
	Student findStudent(int id);
	
	Student createStudent(Student student);
	
	Student updateStudent(Student student);
	
	void removeStudent(Student student);
	
	ArrayList<Student> findStudentsByCourseName(String courseName);
	
	/*
	 * public double totalScorePoints(int studentid); public int totalCredits(int
	 * studentid);
	 */
	public String graduationStatus(int studentid);

	public Student findStudentByName(String username);

	public ArrayList<CourserunStudent> findCurrentCourseByID(Integer id);

	public ArrayList<CourserunStudent> findCompletedCourserunStudentsById(Integer id);

	double totalScorePoints(ArrayList<CourserunStudent> clist);

	int totalCredits(ArrayList<CourserunStudent> clist);

	public ArrayList<Announcement> findAllAnnoucements(List<CourserunStudent> courseruns);

}
