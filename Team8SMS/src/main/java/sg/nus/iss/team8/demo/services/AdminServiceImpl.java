package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.repositories.CourserunRepository;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
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

}
