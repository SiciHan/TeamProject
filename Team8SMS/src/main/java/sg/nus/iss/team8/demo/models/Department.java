package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Department")
public class Department {
	@Id
	@Column(name="id")
	private int departmentId;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "department")
	private List<Faculty> faculties;
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}
	
}
