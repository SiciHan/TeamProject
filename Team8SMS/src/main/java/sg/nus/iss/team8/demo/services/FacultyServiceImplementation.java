package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.repositories.CourserunRepository;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.FacultyRepository;

@Service
public class FacultyServiceImplementation implements FacultyService {

	@Resource
	private FacultyRepository fr;
	private CourserunStudentRepository crsr;
	private CourserunRepository cr;  //repository mingzi 
	
	@Autowired
	public void setCrsr(CourserunStudentRepository crsr) { //zidong lianjie zhege repository
		this.crsr = crsr;
	}
	@Autowired
	public void setFacultyRepository(FacultyRepository fr) {
		this.fr = fr;
	}
	@Autowired
	public void setCourserunRepository(CourserunRepository cr) {
		this.cr = cr;
	}
	@Override  //chongxie shixian fangfa
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
	public ArrayList<Courserun> findAllCourseruns(){
		return fr.findAllCourseruns();
	}
	public List<CourserunStudent> findAllStudents(String courserunname) {
		// TODO Auto-generated method stub
		List<CourserunStudent> result=crsr.findAllByCourserun(courserunname);
		
		if(result==null || result.isEmpty()) {
			return crsr.findAll();
		}
		else
			return result;

	}
	@Override
	public ArrayList<Semester> findAllSemesters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Courserun> findAllCourserunsByFacultyId(int facultyId){
		return cr.findCoursesById(facultyId);
	}
	

}
