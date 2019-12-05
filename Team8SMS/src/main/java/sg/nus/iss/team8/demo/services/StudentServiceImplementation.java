package sg.nus.iss.team8.demo.services;
import java.util.ArrayList;
import java.util.logging.Filter;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;
 
@Service 
public class StudentServiceImplementation implements StudentService{
	private StudentRepository studentRepository;
	private CourserunStudentRepository courserunStudentRepository;
	
	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository; 
	}
	
	@Autowired
	public void setCourserunStudentRepository(CourserunStudentRepository courserunStudentRepository) {
		this.courserunStudentRepository=courserunStudentRepository;
		
	}
	
	@Override
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		//Available =Cancelled; Status =5
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository.findCourseByIdAndStatus(studentid, 5);
		return courses;
	}
	
	@Override
	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		//statusid 4 = Pending
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository.findCourseByIdAndStatus(studentid,4);
		return courses;
	}
	
	@Override
	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		//status id 7 =rejected, statusid 6 =Approved
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository.findCourseByIdAndStatus(studentid, 6);
		courses.addAll(courserunStudentRepository.findCourseByIdAndStatus(studentid,7));
		return courses;
	}	
}
