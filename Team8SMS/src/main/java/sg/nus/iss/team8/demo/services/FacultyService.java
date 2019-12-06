package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import sg.nus.iss.team8.demo.models.Faculty;

public interface FacultyService {

	ArrayList<Faculty> findAllFaculty();
	Faculty findFacultyById(int id);
	Faculty findFacultyByName(String name);
	void deleteFaculty(Faculty f);
	Faculty saveFaculty(Faculty f);
}
