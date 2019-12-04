package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Semesters")
public class Semester {
	@Id
	private int semester;
	
	@Column(length = 50, nullable = false)
	private String label;
	
	@OneToMany(mappedBy = "semester")
	private List<Courserun> courseruns;
	
	@OneToMany(mappedBy = "semester")
	private List<Student> students;
	
	public Semester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Courserun> getCourseruns() {
		return courseruns;
	}

	public void setCourseruns(List<Courserun> courseruns) {
		this.courseruns = courseruns;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	

}
