package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;

import java.util.*;

@Entity
@Table(name = "Status")
public class Status {
	@Id
	@Column(name="statusid")
	
	private int status;
	
	@Column(length = 45, nullable = false)
	private String label;
	
	@OneToMany(mappedBy="status")
	private List<Faculty> faculties;
	
	@OneToMany(mappedBy="status")
	private List<Leave> leaves;
	
	@OneToMany(mappedBy = "status")
	private List<Student> students;
	
	@OneToMany(mappedBy = "status")
	private List<CourserunStudent> courserunstudents;
	
	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<CourserunStudent> getCourserunstudents() {
		return courserunstudents;
	}

	public void setCourserunstudents(List<CourserunStudent> courserunstudents) {
		this.courserunstudents = courserunstudents;
	}

	@Override
	public String toString() {
		return "Status [status=" + status + "]";
	}

	
}
