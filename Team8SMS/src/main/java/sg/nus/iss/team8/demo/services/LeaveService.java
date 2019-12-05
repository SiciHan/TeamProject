package sg.nus.iss.team8.demo.services;

import java.util.Date;

public interface LeaveService {
	public void applyForLeave(String userType, int id, Date startDate, Date endDate, String reason);
}
