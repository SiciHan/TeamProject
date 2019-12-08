package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.CourserunStudent_PK;
import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.repositories.CourserunRepository;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.LeaveRepository;
import sg.nus.iss.team8.demo.repositories.SemesterRepository;
import sg.nus.iss.team8.demo.repositories.StatusRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService {
	@Resource
	private StudentRepository studentRepository;
	private CourserunStudentRepository courserunStudentRepository;
	private CourserunRepository courserunRepository;
	private SemesterRepository semesterRepository;
	private StatusRepository statusRepository;
	private LeaveRepository leaveRepository;
	

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
		this.courserunRepository=courserunRepository;	
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
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		//Cancelled; Status =5
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository.findCourseByIdAndStatus(studentid, 5);
		return courses;
	}

	@Override
	@Transactional
	public Student findStudent(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ArrayList<Student> findAllStudents() {
		ArrayList<Student> sl = (ArrayList<Student>) studentRepository.findAll();
		return sl;
	}

	@Override
	@Transactional
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
	@Transactional
	public Student createStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}

	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}

	@Override
	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		// statusid 4 = Pending
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository
				.findCourseByIdAndStatus(studentid, 4);
		return courses;
	}

	@Override
	@Transactional
	public void removeStudent(Student student) {
		studentRepository.delete(student);
	}

	@Override
	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid) {
		// TODO Auto-generated method stub
		// status id 7 =rejected, statusid 6 =Approved
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository
				.findCourseByIdAndStatus(studentid, 6);
		courses.addAll(courserunStudentRepository.findCourseByIdAndStatus(studentid, 7));
		return courses;
	}

	@Override
	public ArrayList<Courserun> findAvailableCourserun(int studentid) {
		// TODO Auto-generated method stub
		//All courserun falls in current semester minus applied courses (pending status)
		ArrayList<Courserun> courses=(ArrayList<Courserun>) courserunRepository.findCourserunOfCurrentSemester(studentid);
		Iterator<Courserun> iterator=courses.iterator();
		while(iterator.hasNext()) 
		{
			Courserun courserun = iterator.next();
			CourserunStudent_PK id= new CourserunStudent_PK();
			id.setCourserun(courserun);
			try {
				id.setStudent(studentRepository.findById(studentid).get());
			}
			catch(NoSuchElementException e){
				return null;
			}
			if(courserunStudentRepository.existsById(id)) {
				iterator.remove();
			};
		}
		return courses;
	}

	@Override
	public ArrayList<CourserunStudent> findCancelledCourserunStudents(int id) {
		// TODO Auto-generated method stub
		ArrayList<CourserunStudent> courses = (ArrayList<CourserunStudent>) courserunStudentRepository.findCourseByIdAndStatus(id, 5);
		return courses;
	}

	@Override
	public boolean courserunStudentExist(int id, String courseCode, int semesterid) {
		// TODO Auto-generated method stub
		CourserunStudent_PK courserunStudentPK=new CourserunStudent_PK();
		Student student=studentRepository.findById(id).get();
		Courserun courserun=courserunRepository.findByCourseCodeAndSemester(courseCode,semesterRepository.findById(semesterid).get());
		courserunStudentPK.setStudent(student);
		courserunStudentPK.setCourserun(courserun);
		return courserunStudentRepository.existsById(courserunStudentPK);
		
	}

	@Override
	public void setStatusToPending(int id, String courseCode, int semesterid) {
		// TODO Auto-generated method stub
		String coursename=courserunRepository.findByCourseCodeAndSemester(courseCode, semesterRepository.getOne(semesterid)).getCourseName();
		courserunStudentRepository.setStatus(id,coursename,4);//4 is pending
	}

	@Override
	public void createCourserunStudent(int id, String courseCode, int semesterid) {
		// TODO Auto-generated method stub
		CourserunStudent courserunStudent=new CourserunStudent();
		courserunStudent.setGrade("N");
		courserunStudent.setStatus(statusRepository.getOne(4));
		CourserunStudent_PK cspk=new CourserunStudent_PK();
		cspk.setStudent(studentRepository.getOne(id));
		cspk.setCourserun(courserunRepository.findByCourseCodeAndSemester(courseCode, semesterRepository.getOne(semesterid)));
		courserunStudent.setId(cspk);
		courserunStudentRepository.save(courserunStudent);
	}

	@Override
	public void setStatusToCancelled(int id, String courseCode, int semesterid) {
		// TODO Auto-generated method stub
		String coursename=courserunRepository.findByCourseCodeAndSemester(courseCode, semesterRepository.getOne(semesterid)).getCourseName();
		courserunStudentRepository.setStatus(id,coursename,5);//5 is cancelled
	}

	@Override
	public ArrayList<Student> findStudentsByCourseName(String courseName) {
		return (ArrayList<Student>) studentRepository.findStudentsByCourseName(courseName);
	}

	@Override
	public ArrayList<Leave> findAllLeaves() {
		// TODO Auto-generated method stub
		return (ArrayList<Leave>) leaveRepository.findAll();
	}

}
