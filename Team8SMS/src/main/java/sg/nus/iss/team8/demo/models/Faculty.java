package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Faculties")
public class Faculty {
	@Id
	private int facultyId;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Status status;
	
	@OneToMany(mappedBy = "faculty")
	private List<Courserun> Courseruns;
	
	public Faculty() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Courserun> getCourseruns() {
		return Courseruns;
	}

	public void setCourseruns(List<Courserun> courseruns) {
		Courseruns = courseruns;
	}
	
	
}
