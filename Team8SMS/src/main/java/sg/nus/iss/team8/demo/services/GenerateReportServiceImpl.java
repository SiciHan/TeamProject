package sg.nus.iss.team8.demo.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import sg.nus.iss.team8.demo.models.CourserunStudent;

@Service
public class GenerateReportServiceImpl implements GenerateReportService {

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
}
