package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team8.demo.models.Status;

public interface StatusRepository extends JpaRepository<Status,Integer>{

}
