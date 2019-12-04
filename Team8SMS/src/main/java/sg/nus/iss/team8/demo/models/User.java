package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "AllUsers")
public class User {
	@Id
	@Column(length = 30)
	private String username;
	
	@Column(length = 30, nullable = false)
	private String passwordHash;
	
	@Column(length = 50, nullable = false)
	private String userType;
	
	@Column(nullable = false)
	private int id;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
