package sg.nus.iss.team8.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.CourserunStudent_PK;
import sg.nus.iss.team8.demo.models.Student;

public interface CourserunStudentRepository extends JpaRepository<CourserunStudent,CourserunStudent_PK>{
	  @Query("select course from CourserunStudent") 
	  List<Student> findCourseByIdAndStatus(int id, int status);
}
