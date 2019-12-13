package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Announcement_PK implements Serializable {
	private static final long serialVersionUID = 3L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "coursename")
	private String courserunname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourserunname() {
		return courserunname;
	}

	public void setCourserunname(String courserunname) {
		this.courserunname = courserunname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courserunname == null) ? 0 : courserunname.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Announcement_PK other = (Announcement_PK) obj;
		if (courserunname == null) {
			if (other.courserunname != null)
				return false;
		} else if (!courserunname.equals(other.courserunname))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Announcement_PK [id=" + id + ", courserunname=" + courserunname + "]";
	}
	
	
}
