package sg.nus.iss.team8.demo.services;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
	private StudentService studentService;
	
	@Autowired
	public void setStudentService(StudentServiceImplementation studentService) {
		this.studentService = studentService;
	}

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
	public int newFacultyId() {
		ArrayList<Faculty> faculties = findAllFaculty();
		Faculty faculty = Collections.max(faculties, Comparator.comparingInt(Faculty::getFacultyId));
		int newFacultyId = faculty.getFacultyId() + 1;
		return newFacultyId;
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
		
		String sem;
		
		//get current date
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonthValue();
		int currentYear = currentDate.getYear();
		
//		System.out.println("Current YYYY-MM: "+ currentYear + currentMonth);
		
		//if month is >6 -> Sem1, else Sem2
		if (currentMonth > 7 || currentMonth < 2)
			sem = "Sem1";
		else
			sem = "Sem2";
		
		//Extract out the year, eg 2019, add +1 to the year to get next year, 2020
		int nextYear = currentYear+1;
		
		//parse the 2 years to String
		String year1 = String.valueOf(currentYear);
		String year2 = String.valueOf(nextYear);
		
		//AY is fixed.
		//Concatenate everything into a String. Eg AY2019/2020Sem2
		String thisSem = "AY"+year1+"/"+year2+sem;
//		String thisSem = "AY2023/2024Sem1";
//		String thisSem = "AY2018/2019Sem1";
		
//		System.out.println("Current Semester: " + thisSem);
		
		//check if current semester (string) exists in the allSems list (of strings)
		//if exists, just return the semester object
		
		Semester currentSemester = new Semester();
		if(allSems.stream().filter(semester -> semester.getLabel().equals(thisSem)).findFirst().isPresent()) {
			currentSemester = allSems.stream().filter(semester -> semester.getLabel().equals(thisSem)).findFirst().orElse(null);
		} else {
		
		//if no such semester exists,		
		//Instantiate and set Semester label and/or Id 
		//(Id can either manually increment or use generated value annotation>
//		if(currentSemester == null) {
			Semester latestSemesterInTheDatabase = Collections.max(allSems, Comparator.comparingInt(Semester::getSemester));
			int newSemId = latestSemesterInTheDatabase.getSemester() + 1;
			currentSemester.setSemester(newSemId);
			currentSemester.setLabel(thisSem);
		}
		
//		System.out.println(currentSemester.getLabel());
		
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
	public void approveLeave(String startDate, String userType, int id, int status) {
		Status apprvstatus = statusRepository.findById(status).orElse(null);
		List<Leave> leaves=leaveRepository.findAll();
		for (Leave l : leaves) {
			//if the leave is equal to startDate, userType and id 
			// set status to status(approve or reject)
			if (l.getId().getStartDate().toString().equalsIgnoreCase(startDate) 
					&& l.getId().getUserType().equals(userType) && l.getId().getId() == id) {
				l.setStatus(apprvstatus);
				System.out.println("1st comparison:" +l.getId().getStartDate().toString().equalsIgnoreCase(startDate));
				System.out.println("2st comparison:" +l.getId().getUserType().equals(userType));
				System.out.println("3rd comparison:" + l.getStatus().getStatus());
				System.out.println("4th comparison:" + l.getStatus().getLabel());
				leaveRepository.saveAndFlush(l);
			}
		}
	}
	@Override
	public void rejectLeave(String startDate, String userType, int id, int status) {
		Status apprvstatus = statusRepository.findById(status).orElse(null);
		List<Leave> leaves=leaveRepository.findAll();
		for (Leave l : leaves) {
			//if the leave is equal to startDate, userType and id 
			// set status to status(approve or reject)
			if (l.getId().getStartDate().toString().equalsIgnoreCase(startDate) 
					&& l.getId().getUserType().equals(userType) && l.getId().getId() == id) {
				l.setStatus(apprvstatus);
				System.out.println("1st comparison:" +l.getId().getStartDate().toString().equalsIgnoreCase(startDate));
				System.out.println("2st comparison:" +l.getId().getUserType().equals(userType));
				System.out.println("3rd comparison:" + l.getStatus().getStatus());
				System.out.println("4th comparison:" + l.getStatus().getLabel());
				leaveRepository.saveAndFlush(l);
			}
		}
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
	
	@Override
	public ArrayList<Department> findAllDepartment(){
		return (ArrayList<Department>)departmentRepository.findAll();
	}

	@Override
	public Department findDepartmentById(int id) {
		return departmentRepository.findById(id).orElse(null);
	}

	@Override
	public Department findDepartmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDepartment(Department department) {
		departmentRepository.delete(department);
	}

	@Override
	public Department saveDepartment(Department department) {
		return departmentRepository.saveAndFlush(department);
	}

	@Override
	public int newDepartmentId() {
		ArrayList<Department> departments = findAllDepartment();
		Department department = Collections.max(departments, Comparator.comparingInt(Department::getDepartmentId));
		int newDepartmentId = department.getDepartmentId() + 1;
		return newDepartmentId;
	}
	@Override
	public ArrayList<Semester> findAllSemsters() {
		return (ArrayList<Semester>) semesterRepository.findAllSemesters();
	}

	@Override
	public Semester saveSemester(Semester sem) {
		return semesterRepository.saveAndFlush(sem);
	}

	@Override
	public Semester findOrAddSemester(String semLabel) {
		ArrayList<Semester> allSems = (ArrayList<Semester>)semesterRepository.findAll();

		Semester setSemester = new Semester();
		if(allSems.stream().filter(semester -> semester.getLabel().equals(semLabel)).findFirst().isPresent()) {
			setSemester = allSems.stream().filter(semester -> semester.getLabel().equals(semLabel)).findFirst().orElse(null);
		} else {
			Semester latestSemesterInTheDatabase = Collections.max(allSems, Comparator.comparingInt(Semester::getSemester));
			int newSemId = latestSemesterInTheDatabase.getSemester() + 1;
			setSemester.setSemester(newSemId);
			setSemester.setLabel(semLabel);
		}
		
		saveSemester(setSemester);
		return setSemester;		
	}
	
	@Override
	public Courserun concatCourseNameWithYear(Courserun course, String shortSemLabel) {
		course.setCourseName(course.getCourseName() + " " + shortSemLabel);
		return course;
	}

	@Override
	public void applyGraduatedStatus(Semester sem, int threshold) {
		// retrieve all students
		ArrayList<Student> students = findAllStudents();
		
		for (Student student : students) {
			ArrayList<CourserunStudent>clist=courserunStudentRepository.findCourseGradebyId(student.getStudentId());
			double totalCompletedCredits=0;
			for(CourserunStudent c:clist) {
				totalCompletedCredits+=c.getId().getCourserun().getCourseUnit();
			}
//			int totalCompletedCredits = 100; // to test that semester will change
//			System.out.println("Student's status: " + student.getStatus().getLabel() + ", Total credits: " + totalCompletedCredits + ", Student's Semester: " + student.getSemester().getLabel());
			if (student.getStatus().getStatus() != 3) {
					if(totalCompletedCredits >= threshold) {
						//set student's status to 3 = graduated
						Status stat = new Status();
						stat.setStatus(3);
						student.setStatus(stat);
//						System.out.println(student.getStudentId() + " has Graduated: " + student.getStatus().getStatus());
					} else {
						//change semester to current semester
						student.setSemester(sem);
//						System.out.println(student.getStudentId() + " still enrolled: " + student.getSemester().getLabel());
					}
				saveStudent(student);
			}
		}
	}

	@Override
	public ArrayList<CourserunStudent> findCoursesByStudentId(int studentid) {
		return (ArrayList<CourserunStudent>) courserunStudentRepository.findAllCourseById(studentid);
	}

	@Override
	public int countPendingCourses() {
		return (int)courserunStudentRepository.findCourseByStatus(4).size();
	}

	@Override
	public int countPendingLeaves() {
		return (int)leaveRepository.findLeaveByStatus(4).size();
	}

	@Override
	public ArrayList<Leave> findLeavesByYearMonth(YearMonth ym) {
		ArrayList<Leave> leavelist=new ArrayList<Leave>();
		List<Leave> leaves=leaveRepository.findAll();
		for (Leave leave : leaves) {
			//if the leave startdate is before or equals to the selected yearmonth and enddate is after or equals to selected yearmonth
			//we will add it in
			
			Date startDate=leave.getId().getStartDate();
			Date endDate=leave.getEndDate();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			int month1=cal1.get(Calendar.MONTH);
			int year1=cal1.get(Calendar.YEAR);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			int month2=cal2.get(Calendar.MONTH);
			int year2=cal2.get(Calendar.YEAR);
	
			YearMonth startym=YearMonth.of(year1, month1+1);
			YearMonth endym=YearMonth.of(year2, month2+1);
			
			if((startym.isBefore(ym)||startym.equals(ym)) && (endym.isAfter(ym)||endym.equals(ym))) leavelist.add(leave);
		}
		return leavelist;		
	}

	@Override
	public ArrayList<YearMonth> findAllYearMonths(YearMonth currentym) {
		ArrayList<YearMonth> ymlist=new ArrayList<YearMonth>();
		ymlist.add(currentym);
		for(int i=1;i<3;i++) {
			ymlist.add(currentym.plusMonths(i));
			ymlist.add(currentym.minusMonths(i));
		}
		return ymlist;
	}

	@Override
	public ArrayList<String> findAllUserName(ArrayList<Leave> leaves) {
		ArrayList<String> names=new ArrayList<String>();
		for(Leave l:leaves) {
			if(l.getId().getUserType().equals("Student") ){
				String studentname=studentRepository.findById(l.getId().getId()).get().getName();
				names.add(studentname);}
			else if(l.getId().getUserType().equals("Faculty")) {
				String facultyname=fr.findById(l.getId().getId()).get().getName();
				names.add(facultyname);
			}
			else {
				names.add("");
			}
		}
		return names;
	}

	@Override
	public HashMap<String, Leave> MergeListToMap(ArrayList<Leave> leaves, ArrayList<String> username) {
		HashMap<String,Leave> ul=new HashMap<>();
		for(int i=0;i<leaves.size();i++) {
			ul.put(username.get(i), leaves.get(i));
		}
		return ul;
	}

	@Override
	public double calculateGPA(Student student) {
		ArrayList<CourserunStudent>clist=courserunStudentRepository.findCourseGradebyId(student.getStudentId());
		double totalCompletedCredits=0;
		for(CourserunStudent c:clist) {
			totalCompletedCredits+=c.getId().getCourserun().getCourseUnit();
		}
		double points=studentService.totalScorePoints(student.getStudentId());
		double cap;
		cap=points/totalCompletedCredits;
		cap=Math.round(cap*100.0)/100.0;
		return cap;
	}
}
