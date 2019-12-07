package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CourserunStudent_PK implements Serializable {
	private static final long serialVersionUID = 2L;

	@ManyToOne
	@JoinColumn(name = "studentid")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "coursename")
	private Courserun courserun;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourserunStudent_PK other = (CourserunStudent_PK) obj;
		if (courserun == null) {
			if (other.courserun != null)
				return false;
		} else if (!courserun.equals(other.courserun))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Courserun getCourserun() {
		return courserun;
	}

	public void setCourserun(Courserun courserun) {
		this.courserun = courserun;
	}
	
}
