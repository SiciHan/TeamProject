package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;
import sg.nus.iss.team8.demo.repositories.FacultyRepository;

@Service
public class FacultyServiceImplementation implements FacultyService {

	@Resource
	private FacultyRepository fr;
	private CourserunStudentRepository crsr;
	
	@Autowired
	public void setCrsr(CourserunStudentRepository crsr) {
		this.crsr = crsr;
	}
	@Autowired
	public void setFacultyRepository(FacultyRepository fr) {
		this.fr = fr;
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
	public List<CourserunStudent> findAllStudents(String courserunname) {
		// TODO Auto-generated method stub
		List<CourserunStudent> result=crsr.findAllByCourserun(courserunname);
		
		if(result==null || result.isEmpty()) {
			return crsr.findAll();
		}
		else
			return result;
	}

}
