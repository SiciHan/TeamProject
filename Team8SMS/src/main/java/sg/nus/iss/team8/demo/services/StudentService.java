package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import sg.nus.iss.team8.demo.models.CourserunStudent;
@Service

public interface StudentService {
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid);
}