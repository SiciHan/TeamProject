package sg.nus.iss.team8.demo.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.CourserunStudent_PK;
import sg.nus.iss.team8.demo.models.Student;

@Repository
public interface CourserunStudentRepository extends JpaRepository<CourserunStudent,CourserunStudent_PK>{
	  @Query("select course from CourserunStudent course where course.status.status= :status and course.id.student.studentId=:studentId") 
	  public List<CourserunStudent> findCourseByIdAndStatus(@Param("studentId") Integer studentId, @Param("status") Integer status);
	  
	  @Transactional
	  @Modifying
	  @Query("update CourserunStudent crs Set crs.status.status=:status where crs.id.student.studentId =:studentId and crs.id.courserun.courseName=:coursename")
	  public void setStatus(@Param("studentId") Integer studentId, @Param("coursename") String coursename, @Param("status") Integer status);

	  @Query("select course from CourserunStudent course where course.status.status= :status") 

	  public List<CourserunStudent> findCourseByStatus(@Param("status") Integer status);
	  
	  		// SH
		  @Query("Select c From CourserunStudent c where c.id.student.studentId=?1 and (c.status.status=6 or c.status.status=8)")
		  public ArrayList<CourserunStudent>findCourseById(int studentid);
		  // SH
	/*
	 * @Query("Select c from CourserunStudent c where c.id.student.studentId=?1 and c.status.status=9"
	 * ) public ArrayList<CourserunStudent>findCourseGradebyId(int studentid);
	 */

	/*
	 * //SH
	 * 
	 * @Query("Select c from CourserunStudent c where c.id.student.studentId=?1")
	 * public CourserunStudent findStudentNamebyId(int studentid);
	 */

	  

	  @Query("select course from CourserunStudent course where course.id.courserun.courseName=:coursename")
	  public List<CourserunStudent> findAllByCourserun(@Param("coursename") String coursename);
	  
	  @Query("select crs from CourserunStudent crs where crs.id.courserun.courseName = ?1")
	  public ArrayList<CourserunStudent> findStudentsByCourseName(String name);
	  
	  @Query("Select c From CourserunStudent c where c.id.student.studentId=?1")
	  public ArrayList<CourserunStudent>findAllCourseById(int studentid);

}
