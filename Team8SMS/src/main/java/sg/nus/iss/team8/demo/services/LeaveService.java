
package sg.nus.iss.team8.demo.services;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sg.nus.iss.team8.demo.models.Leave;

public interface LeaveService {
	public void applyForLeave(String userType,int id, Date startDate, Date endDate, String reason);
	public ArrayList<Leave> findLeavesByYearMonth(YearMonth ym);
	public ArrayList<YearMonth> findAllYearMonths(YearMonth currentym);
}