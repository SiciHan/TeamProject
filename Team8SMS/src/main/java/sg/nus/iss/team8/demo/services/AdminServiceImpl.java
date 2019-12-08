package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import sg.nus.iss.team8.demo.repositories.CourserunRepository;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.DepartmentRepository;
import sg.nus.iss.team8.demo.repositories.FacultyRepository;
import sg.nus.iss.team8.demo.repositories.LeaveRepository;
import sg.nus.iss.team8.demo.repositories.SemesterRepository;
import sg.nus.iss.team8.demo.repositories.StatusRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Resource
	private StudentRepository studentRepository;
	private CourserunStudentRepository courserunStudentRepository;
	private CourserunRepository courserunRepository;
	private SemesterRepository semesterRepository;
	private StatusRepository statusRepository;
	private LeaveRepository leaveRepository;
	private FacultyRepository fr;
	private DepartmentRepository departmentRepository;

	@Autowired
	public void setFacultyRepository(FacultyRepository fr) {
		this.fr = fr;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setCourserunStudentRepository(CourserunStudentRepository courserunStudentRepository) {
		this.courserunStudentRepository = courserunStudentRepository;
	}

	@Autowired
	public void setCourserunRepository(CourserunRepository courserunRepository) {
		this.courserunRepository = courserunRepository;
	}

	@Autowired
	public void setSemesterRepository(SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	@Autowired
	public void setStatusRepository(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	@Autowired
	public void setLeaveRepository(LeaveRepository leaveRepository) {
		this.leaveRepository = leaveRepository;
	}

	@Autowired
	public void setDepartmentRepository(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public ArrayList<Student> findAllStudents() {
		ArrayList<Student> sl = (ArrayList<Student>) studentRepository.findAll();
		return sl;
	}

	@Override
	public Page<Student> pageAllStudents(Pageable pageable) {
		ArrayList<Student> students = findAllStudents();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Student> newStudentsList;

		if (students.size() < startItem) {
			newStudentsList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, students.size());
			newStudentsList = students.subList(startItem, toIndex);
		}

		Page<Student> studentPage = new PageImpl<Student>(newStudentsList, PageRequest.of(currentPage, pageSize),
				students.size());

		return studentPage;
	}

	@Override
	public Student findStudent(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}

	@Override
	public void removeStudent(Student student) {
		studentRepository.delete(student);
	}

	@Override
	public ArrayList<Faculty> findAllFaculty() {
		ArrayList<Faculty> alf = (ArrayList<Faculty>)fr.findAll();
		return alf;
	}

	@Override
	public Faculty findFacultyById(int id) {
		return fr.findById(id).orElse(null);
	}

	@Override
	public Faculty findFacultyByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Faculty saveFaculty(Faculty f) {
		return fr.saveAndFlush(f);
	}

	@Override
	public void deleteFaculty(Faculty f) {
		fr.delete(f);
	}
	
	@Override
	public int newStudentId() {
		ArrayList<Student> students = findAllStudents();
		Student student = Collections.max(students, Comparator.comparingInt(Student::getStudentId));
		int newStudentId = student.getStudentId() + 1;
		return newStudentId;
	}
	
	@Override
	public Semester currentSemester() {
		ArrayList<Semester> allSems = (ArrayList<Semester>)semesterRepository.findAll();
		Semester currentSemester = Collections.max(allSems, Comparator.comparingInt(Semester::getSemester));
		return currentSemester;
	}
	
	@Override
	public Page<CourserunStudent> pagePendingStudents(Pageable pageable) {
		ArrayList<CourserunStudent> pendingStudents = (ArrayList<CourserunStudent>)courserunStudentRepository.findCourseByStatus(4);

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<CourserunStudent> newCrsList;

		if (pendingStudents.size() < startItem) {
			newCrsList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, pendingStudents.size());
			newCrsList = pendingStudents.subList(startItem, toIndex);
		}

		Page<CourserunStudent> studentPage = new PageImpl<CourserunStudent>(newCrsList, PageRequest.of(currentPage, pageSize),
				pendingStudents.size());

		return studentPage;
	} 
	
	@Override
	public void setCourserunStudentStatus(int id, String courseCode, int semesterid, int status) {
		String coursename=courserunRepository.findByCourseCodeAndSemester(courseCode, semesterRepository.getOne(semesterid)).getCourseName();
		courserunStudentRepository.setStatus(id,coursename,status);
	}
	
	//Willis 7th Dec
	@Override
	public ArrayList<Status> findAllStatuses(){
		return (ArrayList<Status>)statusRepository.findAll();
	}
	@Override
	public ArrayList<Department> findAllDepartments(){
		return (ArrayList<Department>)departmentRepository.findAll();
	}
	@Override
	public ArrayList<Leave> findAllLeave() {
		ArrayList<Leave> listleave = (ArrayList<Leave>) leaveRepository.findAll();
		return listleave;
	}
	@Override
	public Leave findLeave(Leave_PK id) {
		return leaveRepository.findById(id).orElse(null);
	}
	@Override
	public void approveLeave(Leave leave) {
		leaveRepository.setStatus(leave.getId(), 6);
//		leaveRepository.save(leave);
	}
	@Override
	public void rejectLeave(Leave leave) {
		leaveRepository.setStatus(leave.getId(), 7);
	}

	@Override
	public Page<Courserun> pageCourserun(Pageable pageable) {
		ArrayList<Courserun> courseruns = (ArrayList<Courserun>)courserunRepository.findAll();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Courserun> newCourseList;

		if (courseruns.size() < startItem) {
			newCourseList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, courseruns.size());
			newCourseList = courseruns.subList(startItem, toIndex);
		}

		Page<Courserun> courserunPage = new PageImpl<Courserun>(newCourseList, PageRequest.of(currentPage, pageSize),
				courseruns.size());

		return courserunPage;
	} 
	
	@Override
	public Courserun findCourserun(String courseCode, int semesterid) {
		Semester semester = semesterRepository.findById(semesterid).orElse(null);
		return courserunRepository.findByCourseCodeAndSemester(courseCode, semester);
	}

	@Override
	public Courserun saveCourserun(Courserun course) {
		return courserunRepository.saveAndFlush(course);
	}

	@Override
	public void removeCourserun(Courserun course) {
		courserunRepository.delete(course);
	}
	
	@Override
	public ArrayList<CourserunStudent> findStudentsByCourseName(String courseName) {
		return (ArrayList<CourserunStudent>) courserunStudentRepository.findStudentsByCourseName(courseName);
	}

}
