package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "Students")
public class Student {
	@Id
	private int studentId;
	
	@Size(min=2, max=50, message="Name should be between 2 and 50 characters")
	@Column(length = 50, nullable = false)
	private String name;
	
	@NotNull(message="Please pick a gender")
	@Column(length = 10, nullable = false)
	private String gender;
	
	@NotNull(message="Please enter a date")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthDate;
	
	@Column(length = 45, nullable = false)
	private String degree;
	
	@Column(length = 100, nullable = false)
	private String address;
	
	@Column(length = 12, nullable = false)
	private String mobile;
	
	@Email(regexp="^(.+)@(.+)$", message="Invalid email")
	@Column(length = 45, nullable = false)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "semester", nullable = false)
	private Semester semester;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Status status;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
