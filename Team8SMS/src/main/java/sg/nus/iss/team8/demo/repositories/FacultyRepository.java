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
	 
}
