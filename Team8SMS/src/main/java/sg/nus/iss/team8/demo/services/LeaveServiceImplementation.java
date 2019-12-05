package sg.nus.iss.team8.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Leave;
import sg.nus.iss.team8.demo.repositories.LeaveRepository;

@Service
public class LeaveServiceImplementation implements LeaveService {

	private LeaveRepository<Leave> lr;
	
	@Autowired
	public void setLeaveRepository(LeaveRepository<Leave> lr) {
		this.lr=lr;
	}
	
	@Override
	public void applyForLeave(String userType, int id, Date startDate, Date endDate, String reason) {
		// TODO Auto-generated method stub

	}

}
