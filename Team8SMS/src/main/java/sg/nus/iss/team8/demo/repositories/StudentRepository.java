package sg.nus.iss.team8.demo.repositories;
  
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.nus.iss.team8.demo.models.Student;
  
@Repository 
public interface StudentRepository extends JpaRepository<Student,Integer>{
	@Query("select cs.id.student from CourserunStudent cs where cs.id.courserun.courseName = ?1")
	public ArrayList<Student> findStudentsByCourseName(String name);
	
	@Query("Select distinct s From Student s where s.studentId=?1")
	public Student findNameById(int id);
	
	@Query("select s from Student s, User u where s.studentId=u.id and u.username=:username")
	public Student findByUserName(String username);
}
 