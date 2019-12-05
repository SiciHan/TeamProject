package sg.nus.iss.team8.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.CourserunStudent_PK;

@Repository
public interface CourserunStudentRepository extends JpaRepository<CourserunStudent,CourserunStudent_PK>{
	  @Query("select course from CourserunStudent course where course.status.status= :status and course.id.student.studentId=:studentId") 
	  public List<CourserunStudent> findCourseByIdAndStatus(@Param("studentId") Integer studentId, @Param("status") Integer status);
}
