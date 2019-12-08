package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	ArrayList<Faculty> findAllFaculty();

	Faculty findFacultyById(int id);

	Faculty findFacultyByName(String name);

	void deleteFaculty(Faculty f);

	Faculty saveFaculty(Faculty f);
	
	//Willis added
	ArrayList<Status> findAllStatuses();
	ArrayList<Department> findAllDepartments();
	ArrayList<Leave> findAllLeave();
	Leave findLeave(Leave_PK id);
	void approveLeave(Leave leave);
	void rejectLeave(Leave leave);
}
