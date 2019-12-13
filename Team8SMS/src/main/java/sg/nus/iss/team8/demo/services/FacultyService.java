package sg.nus.iss.team8.demo.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;

import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.models.Student;
@Service
public interface FacultyService {

	ArrayList<Faculty> findAllFaculty();
	Faculty findFacultyById(int id);
	Faculty findFacultyByName(String name);
	void deleteFaculty(Faculty f);
	Faculty saveFaculty(Faculty f);
	
	ArrayList<Semester> findAllSemesters();
	ArrayList<Courserun> findAllCourseruns();
	ArrayList<Courserun> findAllCourserunsByFacultyId(int facultyId);
	List<CourserunStudent> findAllStudents(String courserunname);
	List<CourserunStudent> findAllByCourserunandgrade(String courserunname, String grade);
	List<CourserunStudent> findAllCourserunStudentList(String courserunname, String grade);
	
	CourserunStudent saveCourserunStudent(CourserunStudent courserunStudent);
	List<CourserunStudent> saveCourserunStudents(List<CourserunStudent> courserunStudents);
	Faculty findFacultyByUserName(String username);
}


