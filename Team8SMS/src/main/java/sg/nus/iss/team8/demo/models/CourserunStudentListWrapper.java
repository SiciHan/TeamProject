package sg.nus.iss.team8.demo.models;

import java.util.ArrayList;
import java.util.List;

public class CourserunStudentListWrapper {

		private List<CourserunStudent> courserunStudents;

		public List<CourserunStudent> getList(){
		    return courserunStudents;
		}

		public void setList(List<CourserunStudent> courserunStudents){
		    this.courserunStudents = courserunStudents;
		}
	}