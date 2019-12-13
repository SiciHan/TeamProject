package sg.nus.iss.team8.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "announcement")
public class Announcement {

	@EmbeddedId
	private Announcement_PK id;
	
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Announcement_PK getId() {
		return id;
	}

	public void setId(Announcement_PK id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return message;
	}
	
}
