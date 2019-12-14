package sg.nus.iss.team8.demo.repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.nus.iss.team8.demo.models.*;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Announcement_PK> {
	
	@Query("Select a from Announcement a where a.id.courserunname=:courserunName")
	public ArrayList<Announcement> findByCourserunName(String courserunName);
}
