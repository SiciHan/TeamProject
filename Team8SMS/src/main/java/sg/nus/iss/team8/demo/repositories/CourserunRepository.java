package sg.nus.iss.team8.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.nus.iss.team8.demo.models.*;

@Repository
public interface CourserunRepository extends JpaRepository<Courserun,String> {
	@Query("select f.Courseruns from Faculty f where f.facultyId = ?1")
	public ArrayList<Courserun> findCoursesById(int facultyId);
}
