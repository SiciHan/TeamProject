package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Courseruns")
public class Courserun {
	@Id
	@Column(length = 50)
	private String courseName;
	
	@Column(length = 8, nullable = false)
	private String courseCode;
	
	@Column(nullable = false)
	private int courseUnit;
	
	@ManyToOne
	@JoinColumn(name = "faculityId", nullable = false)
	private Faculty faculty;
	
	@ManyToOne
	@JoinColumn(name = "semester", nullable = false)
	private Semester semester;

	public Courserun() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getCourseUnit() {
		return courseUnit;
	}

	public void setCourseUnit(int courseUnit) {
		this.courseUnit = courseUnit;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
}
