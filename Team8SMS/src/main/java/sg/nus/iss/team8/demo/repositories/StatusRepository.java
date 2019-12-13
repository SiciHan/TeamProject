package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team8.demo.models.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
	@Query("select f.status from Faculty f where f.facultyId = ?1")
	public Status findFacultyStatusById(int facultyId);
	
	@Query("select s from Status s where s.status=?1")
	public Status findByStatusId(int status);
}
