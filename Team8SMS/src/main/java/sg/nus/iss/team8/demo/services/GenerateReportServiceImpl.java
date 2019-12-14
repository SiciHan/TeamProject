package sg.nus.iss.team8.demo.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.CourserunStudent;
import sg.nus.iss.team8.demo.models.Student;
import sg.nus.iss.team8.demo.repositories.CourserunStudentRepository;

@Service
public class GenerateReportServiceImpl implements GenerateReportService {
	
	@Autowired
	private CourserunStudentRepository crsr;
	
	@Override
	public <T> void ExportCSV(HttpServletRequest request, HttpServletResponse response, List<T> list, String[] headers)
			throws IOException {
		// TODO Auto-generated method stub

		// set http header
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment;filename=output.csv");
		response.setContentType("text/csv");
		response.setHeader(headerKey, headerValue);

		CSVWriter writer = new CSVWriter(response.getWriter());

		writer.writeNext(headers);
		for (T entry : list) {
			String[] output = entry.toString().split(",");
			writer.writeNext(output);
		}
		writer.close();
	}

	@Override
	public void ExportTranscript(HttpServletRequest request, HttpServletResponse response, Student student,
			ArrayList<CourserunStudent> clist, int totalCredits, double cap, String gstatus) throws IOException {
		// TODO Auto-generated method stub
		//First I need to print the student's detail
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment;filename=transcript.csv");
		response.setContentType("text/csv");
		response.setHeader(headerKey, headerValue);

		CSVWriter writer = new CSVWriter(response.getWriter());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		String[] row1=new String[] {"Name",student.getName(), 
								"Student No", Integer.toString(student.getStudentId()),
								"Date of Birth", student.getBirthDate().toString(),
								"Date of Issue", today};
		writer.writeNext(row1);
		writer.writeNext(new String[] {});
		
		String[] row2= new String[] {"Course Code","Course Name", "Semester", "Faculty", "Course Unit", "Courses Status", "Grade"};
		writer.writeNext(row2);
		for(CourserunStudent c: clist) {
			String[] output=new String[] {
					c.getId().getCourserun().getCourseCode(),
					c.getId().getCourserun().getCourseName(),
					c.getId().getCourserun().getSemester().getLabel(),
					c.getId().getCourserun().getFaculty().getName(),
					c.getId().getCourserun().getCourseUnit()+"",
					c.getStatus().getLabel(),
					c.getGrade(),
											
			};
			writer.writeNext(output);
		}
		String[] row4= new String[] {"Total Modular Credits",Integer.toString(totalCredits)};
		writer.writeNext(row4);
		String[] row5=new String[] {"Cumulative Average Point",Double.toString(cap)};
		writer.writeNext(row5);
		String[] row6=new String[] {"Remaining Modular Credits to fullfill", (160-totalCredits>0)?Integer.toString(160-totalCredits):"0"};
		writer.writeNext(row6);
		String[] row7=new String[] {"Graduation Status",gstatus};
		writer.writeNext(row7);
		writer.close();
	}

	@Override
	public void ExportCombinedCSV(HttpServletRequest request, HttpServletResponse response,
			ArrayList<Courserun> toprintlist) throws IOException {
		// TODO Auto-generated method stub
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment;filename=output.csv");
		response.setContentType("text/csv");
		response.setHeader(headerKey, headerValue);
		CSVWriter writer = new CSVWriter(response.getWriter());
		String[] headers=new String[]{"Course Name","Student Id", "Student Name", "Degree", "Mobile", "Email", "Grade", "Status"};
		writer.writeNext(headers);
		for(Courserun cr:toprintlist) {
			String n=cr.getCourseName();
			List<CourserunStudent> list=crsr.findAllByCourserun(n);
			for (CourserunStudent entry : list) {
				String[] output = entry.toString().split(",");
				writer.writeNext(output);
			}
		}
		writer.close();
		
	}
}
