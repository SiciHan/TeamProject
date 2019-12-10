package sg.nus.iss.team8.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.nus.iss.team8.demo.models.*;

@Repository
public interface EmailRepository extends JpaRepository<CourserunStudent, CourserunStudent_PK> {
	@Query("select cs.id.student.email from CourserunStudent cs where cs.id.courserun.courseName = ?1")
	public ArrayList<String> findAllEmailByCoursename(String coursename);
}
