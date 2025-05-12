package org.java.hibernate.compositekey;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Enrollment {

	@EmbeddedId
	private EnrollmentId id;

	private String semester;

	public Enrollment() {
	}

	public Enrollment(EnrollmentId id, String semester) {
		this.id = id;
		this.semester = semester;
	}

	// Getters and Setters
	public EnrollmentId getId() {
		return id;
	}

	public void setId(EnrollmentId id) {
		this.id = id;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
}
