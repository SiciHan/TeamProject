package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "announcement")
public class Announcement {

	@EmbeddedId
	private Announcement_PK id;
	
	@NotEmpty(message = "Announcement content must not be empty !")
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
