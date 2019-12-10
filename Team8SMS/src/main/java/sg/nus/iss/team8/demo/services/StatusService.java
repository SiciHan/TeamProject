package sg.nus.iss.team8.demo.services;

import java.time.YearMonth;
import java.util.*;
import org.springframework.stereotype.Service;
import sg.nus.iss.team8.demo.models.*;

@Service
public interface StatusService {
	public Status findByStatusId(int status);
	public void saveStatus(Status status);
}
