package sg.nus.iss.team8.demo.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public interface GenerateReportService {
	//public void ExportCSV(HttpServletRequest request, HttpServletResponse response, List list);

	public <T> void ExportCSV(HttpServletRequest request, HttpServletResponse response, List<T> list, String[] headers) throws IOException;
}
