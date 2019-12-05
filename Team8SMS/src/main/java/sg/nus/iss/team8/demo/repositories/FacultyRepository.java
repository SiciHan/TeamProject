package sg.nus.iss.team8.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.nus.iss.team8.demo.models.*;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
	@Query("select f.Courseruns from Faculty f where f.facultyId = ?1")
	public ArrayList<Courserun> findCourses(int facultyId);
	
	@Query("select cs.id.student from CourserunStudent cs where cs.id.courserun.courseName = ?1")
	public ArrayList<Student> findStudentsByCourseName(String name);
}
