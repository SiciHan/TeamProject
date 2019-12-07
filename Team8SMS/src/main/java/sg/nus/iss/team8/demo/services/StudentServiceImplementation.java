//package sg.nus.iss.team8.demo.services;
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired; 
//import org.springframework.stereotype.Service;
//
//import sg.nus.iss.team8.demo.models.CourserunStudent;
//import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
//import sg.nus.iss.team8.demo.repositories.StudentRepository;
// 
//@Service 
//public class StudentServiceImplementation implements StudentService{
//	private StudentRepository studentRepository;
//	private CourserunStudentRepository courserunStudentRepository;
//	
//	@Autowired
//	public void setStudentRepository(StudentRepository studentRepository) {
//		this.studentRepository = studentRepository; 
//	}
//	
//	@Autowired
//	public void setCourserunStudentRepository(CourserunStudentRepository courserunStudentRepository) {
//		this.courserunStudentRepository=courserunStudentRepository;
//	}
//	
//	@Override
//	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@Override
//	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@Override
//	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid) {
//		// TODO Auto-generated method stub
//		return null;
//	}	
//}
