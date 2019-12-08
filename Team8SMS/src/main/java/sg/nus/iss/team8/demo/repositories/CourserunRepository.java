package sg.nus.iss.team8.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.Semester;
@Repository
public interface CourserunRepository extends JpaRepository<Courserun,String> {

	@Query("Select courserun From Courserun courserun, Student student where courserun.semester.semester=student.semester.semester and student.studentId=:studentid")
	ArrayList<Courserun> findCourserunOfCurrentSemester(@Param("studentid") Integer studentid);

	Courserun findByCourseCodeAndSemester(String courseCode, Semester semester);

	@Query("select f.Courseruns from Faculty f where f.facultyId = ?1")
	public ArrayList<Courserun> findCoursesById(int facultyId);
	
}
