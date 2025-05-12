package org.java.hibernate.eventlistners;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String entityName;
	private String action; // "INSERT", "UPDATE", "DELETE"
	private String details; // Could store entity state or any changes made
	private String username;
	private long timestamp;

	public AuditLog() {
	}

	public AuditLog(String entityName, String action, String details, String username, long timestamp) {
		this.entityName = entityName;
		this.action = action;
		this.details = details;
		this.username = username;
		this.timestamp = timestamp;
	}

	// Getters and setters
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
