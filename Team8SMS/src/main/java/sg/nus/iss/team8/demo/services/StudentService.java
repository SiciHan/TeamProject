package sg.nus.iss.team8.demo.services;

import java.util.ArrayList;

import sg.nus.iss.team8.demo.models.CourserunStudent;

public interface StudentService {
	public ArrayList<CourserunStudent> findAvailableCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findPendingCourserunStudents(int studentid);

	public ArrayList<CourserunStudent> findRejectedAndApprovedCourserunStudents(int studentid);
}
