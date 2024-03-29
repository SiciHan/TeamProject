package sg.nus.iss.team8.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.nus.iss.team8.demo.models.*;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {	
	@Query("SELECT courserun FROM Courserun courserun")
	ArrayList<Courserun> findAllCourseruns();
	 
	@Query("SELECT course from CourserunStudent course where course.id.courserun.courseName=:coursename and course.grade=:grade")
	public List<CourserunStudent> findAllByCourserunandgrade(@Param("coursename") String coursename,@Param("grade")String grade);
	 
	@Query("SELECT course from CourserunStudent course")
	List<CourserunStudent> findAllCourserunStudentList();

	@Query("select f from Faculty f, User u where f.facultyId=u.id and u.username=:username")
	Faculty findByUserName(String username);

}
