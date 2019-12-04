package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "CourserunStudents")
public class CourserunStudent {
	@EmbeddedId
	private CourserunStudent_PK id;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Status status;
	
	@Column(length = 3, nullable = false)
	private String grade;
	
	public CourserunStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourserunStudent_PK getId() {
		return id;
	}

	public void setId(CourserunStudent_PK id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
