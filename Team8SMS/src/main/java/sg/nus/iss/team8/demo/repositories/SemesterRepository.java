package sg.nus.iss.team8.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.nus.iss.team8.demo.models.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Integer>{
	@Query("Select s from Semester s")
	public ArrayList<Semester> findAllSemesters();
	
}
