package sg.nus.iss.team8.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity
@Table(name = "Leaves")
public class Leave {
	@EmbeddedId
	private Leave_PK id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, name="enddate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "Invalid Date!")
	@NotNull(message = "End Date is required!")
	private Date endDate;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Reason is required!")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Status status;
	
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Leave_PK getId() {
		return id;
	}

	public void setId(Leave_PK id) {
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
