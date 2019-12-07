
package sg.nus.iss.team8.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.Leave_PK;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Leave_PK> {

}
