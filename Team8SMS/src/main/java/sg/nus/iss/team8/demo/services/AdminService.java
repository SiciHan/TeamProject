package sg.nus.iss.team8.demo.services;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Department;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.Leave_PK;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.models.Status;
import sg.nus.iss.team8.demo.models.Student;

@Service
public interface AdminService {
	ArrayList<Student> findAllStudents();

	Page<Student> pageAllStudents(Pageable pageable);

	Student findStudent(int id);

	Student saveStudent(Student student);

	void removeStudent(Student student);
	
	//for faculty management
	ArrayList<Faculty> findAllFaculty();

	Faculty findFacultyById(int id);

	Faculty findFacultyByName(String name);

	void deleteFaculty(Faculty f);

	Faculty saveFaculty(Faculty f);
	
	int newFacultyId();
	
int newStudentId();
	
	Semester currentSemester();
	
	Page<CourserunStudent> pagePendingStudents(Pageable pageable);
	
	void setCourserunStudentStatus(int id, String courseCode, int semesterid, int status);
	
	Page<Courserun> pageCourserun(Pageable pageable);

	Courserun findCourserun(String courseCode, int semesterid);

	Courserun saveCourserun(Courserun course);

	void removeCourserun(Courserun course);

	ArrayList<CourserunStudent> findStudentsByCourseName(String courseName);
	
	// for Leave application
	ArrayList<Status> findAllStatuses();
	
	ArrayList<Department> findAllDepartments();
	
	ArrayList<Leave> findAllLeave();
	
	Leave findLeave(Leave_PK id);
	
	void approveLeave(String string, String userType,int id, int status);
	
	void rejectLeave(String startDate, String userType,int id, int status);
}
