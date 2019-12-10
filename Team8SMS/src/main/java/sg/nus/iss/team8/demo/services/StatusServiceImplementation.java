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
import sg.nus.iss.team8.demo.models.Status;
import sg.nus.iss.team8.demo.models.User;
import sg.nus.iss.team8.demo.repositories.*;

@Service
public class StatusServiceImplementation implements StatusService {
	private StatusRepository starepo;
	
	@Autowired
	public void setStatusRepository(StatusRepository starepo) {
		this.starepo = starepo;
	}
	
	@Override
	public Status findByStatusId(int status) {
		return starepo.findByStatusId(status);
	}
	
	@Override
	public void saveStatus(Status status) {
		starepo.saveAndFlush(status);
	}
}
