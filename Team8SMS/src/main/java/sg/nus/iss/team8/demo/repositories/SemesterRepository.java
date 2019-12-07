package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team8.demo.models.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Integer>{

}
