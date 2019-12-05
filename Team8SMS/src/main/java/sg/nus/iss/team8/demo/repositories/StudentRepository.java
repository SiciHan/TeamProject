
  package sg.nus.iss.team8.demo.repositories;
  
  import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import
  org.springframework.stereotype.Repository;
  
  import sg.nus.iss.team8.demo.models.Student;
  
  @Repository 
  public interface StudentRepository extends JpaRepository<Student,Integer>{
  }
 