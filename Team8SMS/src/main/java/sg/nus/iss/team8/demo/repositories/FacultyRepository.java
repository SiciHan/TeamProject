package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team8.demo.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer>{

}
