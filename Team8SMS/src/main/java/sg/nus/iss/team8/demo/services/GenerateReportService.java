package sg.nus.iss.team8.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Student;

@Service
public interface GenerateReportService {
	//public void ExportCSV(HttpServletRequest request, HttpServletResponse response, List list);

	public <T> void ExportCSV(HttpServletRequest request, HttpServletResponse response, List<T> list, String[] headers) throws IOException;


	void ExportTranscript(HttpServletRequest request, HttpServletResponse response, Student student,
			ArrayList<CourserunStudent> clist, int totalCredits, double cap, String gstatus) throws IOException;


	public void ExportCombinedCSV(HttpServletRequest request, HttpServletResponse response,
			ArrayList<Courserun> toprintlist) throws IOException;
}
