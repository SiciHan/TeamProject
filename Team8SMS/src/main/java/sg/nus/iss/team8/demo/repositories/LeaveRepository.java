
package sg.nus.iss.team8.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.Leave_PK;
import sg.nus.iss.team8.demo.models.Status;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Leave_PK> {
	  
	@Query("select leave from Leave leave where leave.status.status= :status") 
	public List<Leave> findLeaveByStatus(@Param("status") Integer status);
	
	@Transactional
	@Modifying
	@Query("update Leave leave Set leave.status =:status where leave.id =:leaveId")
	public void setStatus(@Param("leaveId") Leave_PK leaveId, @Param("status") Status status);
}
