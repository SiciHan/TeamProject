
package sg.nus.iss.team8.demo.services;

import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.models.User;
import sg.nus.iss.team8.demo.repositories.FacultyRepository;
import sg.nus.iss.team8.demo.repositories.LeaveRepository;
import sg.nus.iss.team8.demo.repositories.StudentRepository;
import sg.nus.iss.team8.demo.repositories.UserRepository;

@Service
public class LeaveServiceImplementation implements LeaveService {

	private LeaveRepository lr;
	private UserRepository ur;
	private StudentRepository sr;
	private FacultyRepository fr;

	@Autowired
	
	public void setSr(StudentRepository sr) {
		this.sr = sr;
	}

	@Autowired

	public void setFr(FacultyRepository fr) {
		this.fr = fr;
	}

	@Autowired
	public void setLeaveRepository(LeaveRepository lr) {
		this.lr = lr;
	}

	@Autowired
	public void setUserRepository(UserRepository ur) {
		this.ur = ur;
	}
	@Override 
	public void applyForLeave(String userType, int id, Date startDate, Date endDate, String reason) 
	{ // TODO Auto-generated method stub
	}

	/*
	 * @Override public HashMap<YearMonth, ArrayList<Leave>>
	 * findThreeMonthsLeaves(YearMonth currentYearMonth) { // TODO Auto-generated
	 * method stub YearMonth previousYearMonth=currentYearMonth.minusMonths(1);
	 * YearMonth nextYearMonth=currentYearMonth.plusMonths(1);
	 * 
	 * List<Leave> leaves = lr.findAll(); HashMap<YearMonth, ArrayList<Leave>>
	 * leavemap = new HashMap<YearMonth, ArrayList<Leave>>();
	 * leavemap.put(currentYearMonth,new ArrayList<Leave>());
	 * leavemap.put(previousYearMonth,new ArrayList<Leave>());
	 * leavemap.put(nextYearMonth,new ArrayList<Leave>());
	 * 
	 * for (Leave leave : leaves) { for (YearMonth ym: leave.getInvolvedYearMonth())
	 * { if (leavemap.get(ym) != null) { leavemap.get(ym).add(leave); } } } return
	 * leavemap; }
	 */

	@Override
	public ArrayList<Leave> findLeavesByYearMonth(YearMonth ym) {
		// TODO Auto-generated method stub
		
		ArrayList<Leave> leavelist=new ArrayList<Leave>();
		List<Leave> leaves=lr.findAll();
		for (Leave leave : leaves) {
			//if the leave startdate is before or equals to the selected yearmonth and enddate is after or equals to selected yearmonth
			//we will add it in
			
			Date startDate=leave.getId().getStartDate();
			Date endDate=leave.getEndDate();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			int month1=cal1.get(Calendar.MONTH);
			int year1=cal1.get(Calendar.YEAR);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			int month2=cal2.get(Calendar.MONTH);
			int year2=cal2.get(Calendar.YEAR);
	
			YearMonth startym=YearMonth.of(year1, month1+1);
			YearMonth endym=YearMonth.of(year2, month2+1);
			
			if((startym.isBefore(ym)||startym.equals(ym)) && (endym.isAfter(ym)||endym.equals(ym))) leavelist.add(leave);
		}
		return leavelist;
	}

	@Override
	public ArrayList<YearMonth> findAllYearMonths(YearMonth currentym) {
		// TODO Auto-generated method stub
		ArrayList<YearMonth> ymlist=new ArrayList<YearMonth>();
		ymlist.add(currentym);
		for(int i=1;i<3;i++) {
			ymlist.add(currentym.plusMonths(i));
			ymlist.add(currentym.minusMonths(i));
		}
		return ymlist;
	}

	@Override
	public ArrayList<String> findAllUserName(ArrayList<Leave> leaves) {
		// TODO Auto-generated method stub
		ArrayList<String> names=new ArrayList<String>();
		for(Leave l:leaves) {
			if(l.getId().getUserType().equals("Student") ){
				String studentname=sr.findById(l.getId().getId()).get().getName();
				names.add(studentname);}
			else if(l.getId().getUserType().equals("Faculty")) {
				String facultyname=fr.findById(l.getId().getId()).get().getName();
				names.add(facultyname);
			}
			else {
				names.add("");
			}
		}
		return names;
	}

	@Override
	public HashMap<String, Leave> MergeListToMap(ArrayList<Leave> leaves, ArrayList<String> username) {
		// TODO Auto-generated method stub
		HashMap<String,Leave> ul=new HashMap<>();
		for(int i=0;i<leaves.size();i++) {
			ul.put(username.get(i), leaves.get(i));
		}
		return ul;
	}
	
	@Override
	public void saveLeave(Leave leave) {
		lr.saveAndFlush(leave);
	}

}
